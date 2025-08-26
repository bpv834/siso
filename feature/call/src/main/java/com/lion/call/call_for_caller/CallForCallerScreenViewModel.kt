package com.lion.call.call_for_caller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.call_for_caller.usecase.StartCallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CallForCallerScreenViewModel @Inject constructor(
    // uscase
    private val startCallUseCase: StartCallUseCase,
    private val observeCallEventsUseCase: ObserveCallEventsUseCase // AgoraEvent 관찰 유스케이스 주입
) : ViewModel(), CallForCallerScreenViewModelType {
    // 홈 화면의 통화 관련 UI 상태를 관리하는 StateFlow
    private val _callState = MutableStateFlow<CallForCallerState>(CallForCallerState.Idle)
    val callState: StateFlow<CallForCallerState> = _callState.asStateFlow()

    private val _uiState = MutableStateFlow<CallUiState>(CallUiState())
    override val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()

    override fun onClickCall(callerId: Long, receiverId: Long) {
        viewModelScope.launch {
            _callState.value = CallForCallerState.Calling // UI를 '통화 시도 중' 상태로 변경

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
                    _callState.value =
                        CallForCallerState.CallFailed(throwable.message ?: "알 수 없는 오류 발생")
                }
        }
    }

    override fun onClickEndCall() {
        TODO("Not yet implemented")
    }

    override fun onClickBackButton() {
        TODO("Not yet implemented")
    }

    override fun toggleMute() {
        TODO("Not yet implemented")
    }

    override fun toggleSpeaker() {
        TODO("Not yet implemented")
    }

    override fun resetCallState() {
        TODO("Not yet implemented")
    }

    init {
        // ViewModel의 생명주기에 맞춰 코루틴을 실행합니다.
        viewModelScope.launch {
            observeCallEventsUseCase.execute().collect { event ->
                when (event) {
                    is AgoraEvent.CallerJoinedChannel -> {
                        // 발신자(나)가 Agora 채널에 성공적으로 참여했을 때
                        Timber.d("HomeScreenViewModel: CallerJoinedChannel 이벤트 수신 - 통화 활성 상태로 전환")
                        _callState.value = CallForCallerState.CallActive
                        // TODO: 필요하다면 여기서 실제 통화 화면(예: CallActivity)으로
                        // 네비게이션하는 이벤트를 발행할 수 있습니다.
                        // 예: _navigationEvents.emit(NavigationEvent.ToCallScreen(callInfo))
                    }

                    is AgoraEvent.CallError -> {
                        // 통화 시작 중 (또는 Agora SDK에서) 에러 발생 시
                        Timber.e("HomeScreenViewModel: CallError 이벤트 수신 - ${event.message}")
                        _callState.value = CallForCallerState.CallFailed(event.message)
                    }
                    // 다른 AgoraEvent (예: ReceiverJoinedChannel, ReceiverLeftChannel, IncomingCall 등)는
                    // HomeScreenViewModel의 직접적인 관심사가 아닐 수 있습니다.
                    // 이들은 CallViewModel (전역 관리자)에서 처리하여 앱 전반의 UI/로직에 영향을 줍니다.
                    else -> { /* 홈 화면에 직접적인 영향을 주지 않는 이벤트는 무시 */
                    }
                }
            }
        }
    }
}