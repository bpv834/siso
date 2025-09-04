package com.lion.call.call_for_caller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.model.AgoraEvent
import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.usecase.EvaluationAfterCallUseCase
import com.likelion.domain.call_for_caller.usecase.GetUserProfileUseCase
import com.likelion.domain.call_for_caller.usecase.LeaveChannelUseCase
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.call_for_caller.usecase.StartCallUseCase
import com.likelion.domain.call_for_caller.usecase.ToggleMuteUseCase
import com.likelion.domain.call_for_caller.usecase.ToggleSpeakerUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.lion.call.call_for_caller.CallUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
    private val getTokenAllUseCase: GetTokenAllUseCase,
    private val startCallUseCase: StartCallUseCase,
    private val observeCallEventsUseCase: ObserveCallEventsUseCase, // AgoraEvent 관찰 유스케이스 주입
    private val evaluationUseCase: EvaluationAfterCallUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
    private val toggleSpeakerUseCase: ToggleSpeakerUseCase,
    private val toggleMuteUseCase: ToggleMuteUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
) : ViewModel(), CallForCallerScreenViewModelType {
    // 홈 화면의 통화 관련 UI 상태를 관리하는 StateFlow
    /*  private val _callState = MutableStateFlow<CallForCallerState>(CallForCallerState.Idle)
      val callState: StateFlow<CallForCallerState> = _callState.asStateFlow()*/

    private val _uiState = MutableStateFlow<CallUiState>(CallUiState())
    override val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CallUiEvent>()
    override val uiEvent = _uiEvent.asSharedFlow()


    // 토큰변수, ui상태가 아니기때문에 따로 관리
    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState.asStateFlow()

    private val _userIdState = MutableStateFlow<Long?>(null)



    // 시간 관련 일 객체
    private var timerJob: Job? = null


    // 전화 버튼 누르는 메서드
    override fun onClickCall(receiverId: Long) {
        viewModelScope.launch {
            // UI 로딩 상태를 업데이트
            _uiState.update { it.copy(isLoading = true) }
            val accessToken = _tokenState.value

            if (accessToken.isNullOrEmpty()) {
                // 토큰이 없으면 에러 처리
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.emit(CallUiEvent.ShowToast("유효한 토큰이 없어 통화를 시작할 수 없습니다."))
                return@launch
            }

            // 두 프로필을 비동기적으로 가져오기 시작 (병렬 처리)
            val otherProfileDeferred = async {
                getUserProfileUseCase.execute(
                    accessToken = accessToken,
                    userId = receiverId
                )
            }

            val myProfileDeferred = async {
                getUserProfileUseCase.execute(
                    accessToken = accessToken,
                    userId = _userIdState.value!!
                )
            }

            // 두 작업이 모두 완료될 때까지 기다립니다.
            val otherProfileResult = otherProfileDeferred.await()
            val myProfileResult = myProfileDeferred.await()

            // 프로필 로딩 성공 여부 확인 및 UI 업데이트
            if (otherProfileResult.isSuccess && myProfileResult.isSuccess) {
                _uiState.update {
                    it.copy(
                        otherUser = otherProfileResult.getOrThrow(),
                        myUser = myProfileResult.getOrThrow(),
                        isLoading = false
                    )
                }
            } else {
                // 하나라도 실패하면 에러 처리 후 종료
                val exception = otherProfileResult.exceptionOrNull() ?: myProfileResult.exceptionOrNull()
                Timber.e(exception, "프로필 로드 실패")
                _uiState.update { it.copy(errorMessage = exception!!.message, isLoading = false) }
                _uiEvent.emit(CallUiEvent.ShowToast(exception?.message ?: "프로필 로드 실패"))
                return@launch
            }

            val result: Result<CallModel> =
                startCallUseCase.execute(receiverId = receiverId, accessToken = accessToken)
            result
                .onSuccess { callModel ->
                    Timber.d(" StartCallUseCase 성공적으로 실행됨: ${callModel.channelName}")
                    evaluationUseCase.execute(
                        callModel = callModel,
                        isKeepGoing = true,
                        accessToken = accessToken
                    )
                    _uiState.update { it.copy(callProgressState = CallForCallerState.TryConnecting) }

                }
                .onFailure { throwable ->
                    Timber.e(throwable, " 통화 시작 실패 (서버 또는 네트워크 오류)")
                    _uiState.update { it.copy(callProgressState = CallForCallerState.Idle) }
                    _uiEvent.emit(CallUiEvent.ShowToast(throwable.message ?: "통화 시작 실패"))
                }
        }
    }

    // 전화 종료 버튼 클릭 이벤트
    override fun onClickEndCall() {
        // 이 코드는 `Unit`을 반환합니다.
        // 아고라 채널 나가기

        // Todo 상대방에게 나갔다고 전달하기
        viewModelScope.launch {
            // 채널 탈출
            leaveChannelUseCase.execute()
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
        toggleMuteUseCase.execute(_uiState.value.isMuted)
    }

    override fun toggleSpeaker() {
        _uiState.update { currentState ->
            currentState.copy(isSpeakerOn = !_uiState.value.isSpeakerOn)
        }
        toggleSpeakerUseCase.execute(_uiState.value.isSpeakerOn)
    }

    override fun resetCallState() {
        _uiState.update { currentState ->
            currentState.copy(callProgressState = CallForCallerState.Idle)
        }
    }

    override fun onClickReportButton() {
        _uiState.update { currentState ->
            currentState.copy(isOpenReportSheet = !currentState.isOpenReportSheet)
        }
    }

    override fun onClickConfirmPopup() {
        _uiState.update { currentState ->
            currentState.copy(
                isOpenReportSheet = false,
                isOpenConfirmPopup = !currentState.isOpenConfirmPopup
            )
        }
    }

    // 타이머 Job 구현체 (private으로 내부에서만 관리)
    private var _timerJob: Job? = null

    // 통화 화면에서 시간 감소 메서드
    override fun startCallTimer() {
        // 이미 실행 중이 아니라면 새로운 Job을 시작합니다.
        if (timerJob == null || timerJob?.isCancelled == true) {
            timerJob = viewModelScope.launch {
                while (_uiState.value.callDuration > 0) {
                    delay(1000)
                    _uiState.update { currentState ->
                        currentState.copy(callDuration = currentState.callDuration - 1)
                    }
                }

                // 시간이 0이 되면 상태를 종료로 변경하고 이벤트를 발행합니다.
                // 채널 탈출
                leaveChannelUseCase.execute()
                _uiState.update { it.copy(callProgressState = CallForCallerState.CallEnd) }
                stopCallTimer() // 타이머 정리
            }
        }
    }

    // 타이머 중지 메서드
    override fun stopCallTimer() {
        _timerJob?.cancel()
        _timerJob = null
    }

    override fun createChatRoom() {
        TODO("Not yet implemented")
    }

    // ViewModel이 파괴될 때 자동으로 타이머를 중지합니다.
    override fun onCleared() {
        super.onCleared()
        stopCallTimer()
    }

    init {
        // ViewModel의 생명주기에 맞춰 코루틴을 실행합니다.
        viewModelScope.launch {

            // 토큰 Flow를 collect하여 최신 토큰을 항상 유지합니다.
            getTokenAllUseCase.invoke().collect { token ->
                _tokenState.value = token?.accessToken
                _userIdState.value = token?.userInfo?.id
                // 토큰을 받으면 초기 로딩을 완료했음을 알립니다.
                if (token != null) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }


            observeCallEventsUseCase.execute().collect { event ->
                when (event) {
                    // 발신자(Caller)가 채널에 성공적으로 참여했을 때
                    is AgoraEvent.CallerJoinedChannel -> {
                        Timber.d("HomeScreenViewModel: CallerJoinedChannel 이벤트 수신 - 통화 시도 중")
                        _uiState.update { currentState ->
                            currentState.copy(callProgressState = CallForCallerState.TryConnecting) // 상태를 통화 시도로 변경
                        }
                        _uiEvent.emit(ShowToast("채널에 성공적으로 입장했습니다.")) // UI 토스트 표시
                    }

                    // 통화 중 에러가 발생했을 때
                    is AgoraEvent.CallError -> {
                        Timber.e("HomeScreenViewModel: CallError 이벤트 수신 - ${event.message}")
                        _uiEvent.emit(ShowToast("통화 에러 발생: ${event.message}")) // UI 토스트 표시
                        _uiEvent.emit(NavigateUp)
                        leaveChannelUseCase.execute()

                        //  연결중 상태로 테스트
                        /*  _uiState.update { currentState ->
                              currentState.copy(callProgressState = CallForCallerState.Calling) // 상태를 통화 시도로 변경
                          }
                          _uiEvent.emit(ShowToast("채널에 성공적으로 입장했습니다.")) // UI 토스트 표시*/

                        //_uiState.update { it.copy(callProgressState = CallForCallerState.CallActive) } // 상태를 활성 통화로 변경
                       // _uiEvent.emit(ShowToast("수신자가 통화에 참여했습니다.")) // UI 토스트 표시
                    }

                    // 발신자(Caller)가 채널에서 나갔을 때
                    is AgoraEvent.CallerLeftChannel -> {
                        Timber.d("CallerLeftChannel 이벤트 수신 - 통화 종료")
                        leaveChannelUseCase.execute() // 채널 나가기
                        _uiEvent.emit(ShowToast("통화를 종료했습니다.")) // UI 토스트 표시
                        _uiEvent.emit(NavigateUp) // UI 토스트 표시
                    }

                    // 수신자(Receiver)가 채널에 참여했을 때
                    is AgoraEvent.ReceiverJoinedChannel -> {
                        Timber.d("ReceiverJoinedChannel 이벤트 수신 - 통화 활성 상태")
                        _uiState.update { it.copy(callProgressState = CallForCallerState.CallActive) } // 상태를 활성 통화로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화에 참여했습니다.")) // UI 토스트 표시
                    }

                    // 수신자(Receiver)가 채널에서 나갔을 때
                    is AgoraEvent.ReceiverLeftChannel -> {
                        Timber.d(" ReceiverLeftChannel 이벤트 수신 - 통화 종료")
                        leaveChannelUseCase.execute() // 채널 탈출
                        _uiState.update { it.copy(callProgressState = CallForCallerState.CallEnd) } // 상태를 통화 종료로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화를 종료했습니다.")) // UI 토스트 표시
                    }

                    AgoraEvent.CallRejected -> {
                        Timber.d(" CallRejected 이벤트 수신 - 수신자 통화 거절")
                        leaveChannelUseCase.execute()

                    }
                }
            }
        }
    }
}