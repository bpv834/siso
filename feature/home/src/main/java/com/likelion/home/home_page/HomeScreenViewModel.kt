package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.model.AgoraEvent
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.home.usecase.ObserveCallEventsUseCase
import com.likelion.domain.home.usecase.StartCallUseCase
import com.likelion.network.util.AgoraVoiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val startCallUseCase: StartCallUseCase,
    private val observeCallEventsUseCase: ObserveCallEventsUseCase // AgoraEvent 관찰 유스케이스 주입
) : ViewModel(), HomeScreenViewModelType {
    val _userList = MutableStateFlow<List<UsersModel>>(emptyList())
    override val userList : StateFlow<List<UsersModel>> = _userList.asStateFlow()

    // 홈 화면의 통화 관련 UI 상태를 관리하는 StateFlow
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()
    init {
        getUserList()
       // Timber.d("_users : ${_userList.value}")

        // 홈 화면에 특화된 통화 이벤트 구독 (발신자 관점)
        // ViewModel의 생명주기에 맞춰 코루틴을 실행합니다.
        viewModelScope.launch {
            observeCallEventsUseCase.execute().collect { event ->
                when (event) {
                    is AgoraEvent.CallerJoinedChannel -> {
                        // 발신자(나)가 Agora 채널에 성공적으로 참여했을 때
                        Timber.d("HomeScreenViewModel: CallerJoinedChannel 이벤트 수신 - 통화 활성 상태로 전환")
                        _homeUiState.value = HomeUiState.CallActive
                        // TODO: 필요하다면 여기서 실제 통화 화면(예: CallActivity)으로
                        // 네비게이션하는 이벤트를 발행할 수 있습니다.
                        // 예: _navigationEvents.emit(NavigationEvent.ToCallScreen(callInfo))
                    }
                    is AgoraEvent.CallError -> {
                        // 통화 시작 중 (또는 Agora SDK에서) 에러 발생 시
                        Timber.e("HomeScreenViewModel: CallError 이벤트 수신 - ${event.message}")
                        _homeUiState.value = HomeUiState.CallFailed(event.message)
                    }
                    // 다른 AgoraEvent (예: ReceiverJoinedChannel, ReceiverLeftChannel, IncomingCall 등)는
                    // HomeScreenViewModel의 직접적인 관심사가 아닐 수 있습니다.
                    // 이들은 CallViewModel (전역 관리자)에서 처리하여 앱 전반의 UI/로직에 영향을 줍니다.
                    else -> { /* 홈 화면에 직접적인 영향을 주지 않는 이벤트는 무시 */ }
                }
            }
        }
    }

   @Override
   override fun getUserList(){
        viewModelScope.launch {
            _userList.value = getAllUsersUseCase.execute()
        }
    }


    override fun onClickCallButton(callerId: Long, receiverId: Long) {
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Calling // UI를 '통화 시도 중' 상태로 변경
            Timber.d("HomeScreenViewModel: 통화 시작 요청 - callerId: $callerId, receiverId: $receiverId")

            val result = startCallUseCase.execute(callerId = callerId, receiverId = receiverId)
            result
                .onSuccess { callInfo ->
                    Timber.d("HomeScreenViewModel: StartCallUseCase 성공적으로 실행됨: ${callInfo.channelName}")
                    // CallActive 상태는 AgoraEvent.CallerJoinedChannel 콜백에서 처리될 것이므로,
                    // 서버 통신 성공 후 바로 CallActive로 변경하지 않고 'Calling' 상태를 유지합니다.
                    // Agora 채널 조인 성공 후 CallerJoinedChannel 이벤트가 오면 상태가 변경됩니다.
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "HomeScreenViewModel: 통화 시작 실패 (서버 또는 네트워크 오류)")
                    _homeUiState.value = HomeUiState.CallFailed(throwable.message ?: "알 수 없는 오류 발생")
                }
        }
    }

    /**
     * 통화 실패 상태를 초기화하여 다시 통화를 시도할 수 있도록 합니다.
     * 홈 화면에서 통화 실패 메시지를 닫거나 다시 시도할 때 사용될 수 있습니다.
     */
    override fun resetCallState() {
        _homeUiState.value = HomeUiState.Idle
    }
}