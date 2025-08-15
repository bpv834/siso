package com.likelion.login.last_login_info_page

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FakeLastLoginInfoScreenViewModel(
    // usecase
) : LastLoginInfoScreenViewModelType {

    // 1. 뷰모델의 수명주기에 맞춰 하나의 CoroutineScope를 생성합니다.
    private val fakeViewModelScope = CoroutineScope(Job() + Dispatchers.Main)
    // 현 녹음 상태
    private val _recordingState = MutableStateFlow(RecordingState.IDLE)
    override val recordingState: StateFlow<RecordingState> =_recordingState.asStateFlow()
    // 시간 상태
    private val _minuteState = MutableStateFlow(0)
    override val minuteState: StateFlow<Int> = _minuteState.asStateFlow()

    // 코루틴 관리 작업
    private var minuteJob: Job? = null

    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }
    // 녹음 시작 메서드
    override fun startRecording() {
        _minuteState.value = 0
        _recordingState.value = RecordingState.RECORDING
        runRecordingTimer()
    }

    // 타이머 시작 메서드
    override fun runRecordingTimer() {
        minuteJob?.cancel()
        // 2. 미리 생성한 fakeViewModelScope를 사용해 코루틴을 실행합니다.
        minuteJob = fakeViewModelScope.launch {
            while (true) {
                delay(1000L)
                _minuteState.value++
                if(_minuteState.value >=15) {
                    stopRecording()
                }
            }
        }
    }

    // 녹음 정지, 녹음 완료
    override fun stopRecording() {
        _recordingState.value = RecordingState.FINISHED
        minuteJob?.cancel()
        minuteJob = null
    }

    // 3. FakeViewModel의 수명이 끝날 때 모든 코루틴을 취소하는 메서드를 추가합니다.
    fun clear() {
        fakeViewModelScope.cancel()
    }
}
