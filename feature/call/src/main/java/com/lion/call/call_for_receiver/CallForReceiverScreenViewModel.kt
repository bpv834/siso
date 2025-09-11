package com.lion.call.call_for_receiver

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call.model.AgoraEvent
import com.likelion.domain.call.usecase.EvaluationAfterCallUseCase
import com.likelion.domain.call.usecase.GetMyProfileUseCase
import com.likelion.domain.call.usecase.GetUserProfileUseCase
import com.likelion.domain.call.usecase.JoinCallUseCase
import com.likelion.domain.call.usecase.LeaveChannelUseCase
import com.likelion.domain.call.usecase.ObserveCallEventsUseCase
import com.likelion.domain.call.usecase.ToggleMuteUseCase
import com.likelion.domain.call.usecase.ToggleSpeakerUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.lion.call.call_for_caller.CallForCallerScreenViewModelType
import com.lion.call.CallState
import com.lion.call.CallUiEvent
import com.lion.call.CallUiEvent.NavigateUp
import com.lion.call.CallUiEvent.ShowToast
import com.lion.call.CallUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CallForReceiverScreenViewModel @Inject constructor(
    // uscase
    private val getTokenAllUseCase: GetTokenAllUseCase,
    private val observeCallEventsUseCase: ObserveCallEventsUseCase, // AgoraEvent 관찰 유스케이스 주입
    private val evaluationUseCase: EvaluationAfterCallUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
    private val toggleSpeakerUseCase: ToggleSpeakerUseCase,
    private val toggleMuteUseCase: ToggleMuteUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val joinChannelUseCase: JoinCallUseCase,

    ) : ViewModel(), CallForCallerScreenViewModelType {
    // 홈 화면의 통화 관련 UI 상태를 관리하는 StateFlow
    /*  private val _callState = MutableStateFlow<CallForCallerState>(CallForCallerState.Idle)
      val callState: StateFlow<CallForCallerState> = _callState.asStateFlow()*/

    private val initCallerId = savedStateHandle.get<String>("callerId")
    private val initChannelName = savedStateHandle.get<String>("channelName")
    private val initAgoraToken: String? = savedStateHandle.get<String>("encodedToken")?.let { encoded ->
        val decodedBytes = android.util.Base64.decode(
            encoded,
            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
        )
        String(decodedBytes, Charsets.UTF_8) // 실제 Agora Token
    }


    private val _uiState = MutableStateFlow<CallUiState>(CallUiState())
    override val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CallUiEvent>()
    override val uiEvent = _uiEvent.asSharedFlow()


    // 토큰변수, ui상태가 아니기때문에 따로 관리
    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState.asStateFlow()


    fun onEvent(event: CallReceiverEvent) {
        when (event) {
            // 상대, 내 프로필 가져오는 이벤트
            is CallReceiverEvent.Init -> {
                viewModelScope.launch {
                    val userDeferred = async { getUserProfile() }
                    val myDeferred = async { getMyProfile() }

                    try {
                        userDeferred.await()
                    } catch (e: Exception) { /* 에러 처리 */
                    }

                    try {
                        myDeferred.await()
                    } catch (e: Exception) { /* 에러 처리 */
                    }

                    joinChannel()
                }
            }
        }
    }

    suspend fun joinChannel() {
        joinChannelUseCase.execute(agoraToken = initAgoraToken?:"", channelName = initChannelName?:"")

        _uiState.update {
            it.copy(
                isLoading = false,
                callProgressState = CallState.TryConnecting
            )
        }
    }

    // 시간 관련 일 객체
    private var timerJob: Job? = null

    // 유저 가져오기
    suspend fun getUserProfile() {
        Timber.d("agora = $initAgoraToken")
        Timber.d("channel = $initChannelName")
        Timber.d("callerId = $initCallerId")

        val profileResult = getUserProfileUseCase.execute(
            accessToken = _tokenState.value!!,
            userId = initCallerId?.toLong() ?: 9999L
        )

        // 2. Result 객체로 성공/실패 상태 처리
        profileResult.onSuccess { userProfile ->
            // 성공한 경우: UI 상태 업데이트
            _uiState.update { it.copy(otherUser = userProfile) }
            Timber.d("사용자 프로필 로드 성공: $userProfile")
        }.onFailure { exception ->
            // 실패한 경우: 에러 처리 및 함수 종료
            Timber.e(exception, "사용자 프로필 로드 실패")
            // 예를 들어 사용자에게 오류 메시지를 보여주는 로직 추가
            _uiState.update { it.copy(errorMessage = exception.message) }

        }
    }

    suspend fun getMyProfile() {
        val myProfileResult = getMyProfileUseCase.execute(accessToken = _tokenState.value!!)

        myProfileResult.onSuccess { myProfileResult ->
            _uiState.update { it.copy(myUser = myProfileResult) }
            Timber.d("내 프로필 로드 성공: $myProfileResult")
        }.onFailure { exception ->
            // 실패한 경우: 에러 처리 및 함수 종료
            Timber.e(exception, "내 프로필 로드 실패")
            // 예를 들어 사용자에게 오류 메시지를 보여주는 로직 추가
            _uiState.update { it.copy(errorMessage = exception.message) }

        }
    }


    // 수신자쪽에선 안쓸듯
    override fun onClickCall(receiverId: Long) {}


    // 전화 종료 버튼 클릭 이벤트
    override fun onClickEndCall() {
        // 상대방에게 나갔다고 전달하기
        viewModelScope.launch {
            _uiEvent.emit(NavigateUp)
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
            currentState.copy(callProgressState = CallState.Idle)
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

                _uiState.update { it.copy(callProgressState = CallState.CallEnd) }
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
            viewModelScope.launch {
                // 초기 로딩 상태
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        callProgressState = CallState.Idle
                    )
                }

                // 토큰 Flow에서 최초 값 가져오기
                val token = getTokenAllUseCase.invoke().first { it != null }

                // 상태에 저장
                _tokenState.value = token!!.accessToken

                // 최초 한 번만 Init 이벤트 발생
                onEvent(CallReceiverEvent.Init(token.accessToken))

            }
            observeCallEventsUseCase.execute().collect { event ->
                when (event) {
                    // 발신자(Caller)가 채널에 성공적으로 참여했을 때
                    is AgoraEvent.CallerJoinedChannel -> {
                        Timber.d("- 통화 시도 중")
                        _uiState.update { currentState ->
                            currentState.copy(callProgressState = CallState.TryConnecting) // 상태를 통화 시도로 변경
                        }
                        _uiEvent.emit(ShowToast("채널에 성공적으로 입장했습니다.")) // UI 토스트 표시
                    }

                    // 통화 중 에러가 발생했을 때
                    is AgoraEvent.CallError -> {
                        Timber.e("CallError 이벤트 수신 - ${event.message}")
                        _uiEvent.emit(ShowToast("통화 에러 발생: ${event.message}")) // UI 토스트 표시
                        _uiEvent.emit(NavigateUp)
                        leaveChannelUseCase.execute() // 채널 탈출
                        _uiState.update { it.copy(callProgressState = CallState.CallEnd) }
                        _uiEvent.emit(ShowToast("에러 발생 ${event.message}"))
                    }

                    // 발신자(Caller)가 채널에서 나갔을 때
                    is AgoraEvent.CallerLeftChannel -> {
                        Timber.d("CallerLeftChannel 이벤트 수신 - 통화 종료")
                        leaveChannelUseCase.execute() // 채널 탈출
                        _uiEvent.emit(ShowToast("발신자가 채널을 떠났습니다.")) // UI 토스트 표시
                        _uiState.update { it.copy(callProgressState = CallState.CallEnd) }
                    }

                    // 수신자(Receiver)가 채널에 참여했을 때
                    is AgoraEvent.ReceiverJoinedChannel -> {
                        Timber.d("통화 활성 상태")
                        _uiState.update { it.copy(callProgressState = CallState.CallActive) } // 상태를 활성 통화로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화에 참여했습니다.")) // UI 토스트 표시
                    }

                    // 수신자(Receiver)가 채널에서 나갔을 때
                    is AgoraEvent.ReceiverLeftChannel -> {
                        Timber.d("ReceiverLeftChannel 이벤트 수신 - 통화 종료")
                        leaveChannelUseCase.execute() // 채널 탈출
                        _uiState.update { it.copy(callProgressState = CallState.CallEnd) } // 상태를 통화 종료로 변경
                        _uiEvent.emit(ShowToast("수신자가 통화를 종료했습니다.")) // UI 토스트 표시
                    }

                    AgoraEvent.CallRejected -> {
                        Timber.d("수신자쪽에서 거절당하나?")
                        leaveChannelUseCase.execute()// 채널 탈출
                    }
                }
            }
        }
    }
}