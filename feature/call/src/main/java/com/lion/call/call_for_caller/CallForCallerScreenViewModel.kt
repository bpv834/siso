package com.lion.call.call_for_caller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.call_for_caller.usecase.StartCallUseCase
import com.lion.call.call_for_caller.CallUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _uiEvent = MutableSharedFlow<CallUiEvent>()
    override val uiEvent = _uiEvent.asSharedFlow()

    // 전화 버튼 누르는 메서드
    override fun onClickCall(callerId: Long, receiverId: Long) {
        viewModelScope.launch {
            // 현재 전화 상태 변경
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
                    _callState.value = CallForCallerState.Idle // 통화 초기상태로 변경
                    // 필요하면 토스트 이벤트도 발생시킬 수 있음
                    _uiEvent.emit(CallUiEvent.ShowToast(throwable.message ?: "통화 시작 실패"))
                }
        }
    }
    // 전화 종료 버튼 클릭 이벤트
    override fun onClickEndCall() {
        // 이 코드는 `Unit`을 반환합니다.
        viewModelScope.launch {
            _uiEvent.emit(CallUiEvent.NavigateUp)
        }

    }

    // 뒤로가기 버튼은 뒤로가기 이벤트만 발생시키고 처리는 뷰에서 한다.
    override fun onClickBackButton() {
        viewModelScope.launch {
            _uiEvent.emit(NavigateUp)
        }
    }

    override fun toggleMute() {
        // 이 코드는 `Unit`을 반환합니다.
        _uiState.update { currentState ->
            currentState.copy(isMuted = !_uiState.value.isMuted)
        }
    }

    override fun toggleSpeaker() {
        _uiState.update { currentState ->
            currentState.copy(isSpeakerOn = !_uiState.value.isSpeakerOn)
        }
    }

    override fun resetCallState() {
      _uiState.update { currentState->
          currentState.copy(callProgressState = CallForCallerState.Idle)
      }
    }

    init {
        // ViewModel의 생명주기에 맞춰 코루틴을 실행합니다.
        viewModelScope.launch {
            observeCallEventsUseCase.execute().collect { event ->
                when (event) {
                    // 발신자(Caller)가 채널에 성공적으로 참여했을 때
                    is AgoraEvent.CallerJoinedChannel -> {
                        Timber.d("HomeScreenViewModel: CallerJoinedChannel 이벤트 수신 - 통화 시도 중")
                        _uiState.update { currentState ->
                            currentState.copy(callProgressState = CallForCallerState.Calling) // 상태를 통화 시도로 변경
                        }
                        _uiEvent.emit(ShowToast("채널에 성공적으로 입장했습니다.")) // UI 토스트 표시
                    }

                    // 통화 중 에러가 발생했을 때
                    is AgoraEvent.CallError -> {
                        Timber.e("HomeScreenViewModel: CallError 이벤트 수신 - ${event.message}")
                        _uiEvent.emit(ShowToast("통화 에러 발생: ${event.message}")) // UI 토스트 표시
                        _uiEvent.emit(NavigateUp) // UI 토스트 표시

                    }

                    // 발신자(Caller)가 채널에서 나갔을 때
                    is AgoraEvent.CallerLeftChannel -> {
                        Timber.d("HomeScreenViewModel: CallerLeftChannel 이벤트 수신 - 통화 종료")
                        _uiEvent.emit(ShowToast("통화를 종료했습니다.")) // UI 토스트 표시
                        _uiEvent.emit(NavigateUp) // UI 토스트 표시
                    }

                    // 수신자(Receiver)가 채널에 참여했을 때
                    is AgoraEvent.ReceiverJoinedChannel -> {
                        Timber.d("HomeScreenViewModel: ReceiverJoinedChannel 이벤트 수신 - 통화 활성 상태")
                        _uiState.update { it.copy(callProgressState = CallForCallerState.CallActive) } // 상태를 활성 통화로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화에 참여했습니다.")) // UI 토스트 표시
                    }

                    // 수신자(Receiver)가 채널에서 나갔을 때
                    is AgoraEvent.ReceiverLeftChannel -> {
                        Timber.d("HomeScreenViewModel: ReceiverLeftChannel 이벤트 수신 - 통화 종료")
                        _uiState.update { it.copy(callProgressState = CallForCallerState.CallEnd) } // 상태를 통화 종료로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화를 종료했습니다.")) // UI 토스트 표시
                    }
                }
            }
        }
    }
}