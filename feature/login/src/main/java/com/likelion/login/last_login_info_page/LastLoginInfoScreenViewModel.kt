package com.likelion.login.last_login_info_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel @Inject constructor(
    private val audioRecorder: AudioRecorderClass, // Hilt로 AudioRecorder 인스턴스를 주입받음
    // usecase
) : ViewModel(), LastLoginInfoScreenViewModelType {
    // 초기 상태는 녹음 전상태
    private val _recordingState = MutableStateFlow(RecordingState.IDLE)
    override val recordingState: StateFlow<RecordingState> = _recordingState.asStateFlow()

    // 초기 상태는 0 초
    private val _secondsState = MutableStateFlow(0)
    override val secondsState: StateFlow<Int> = _secondsState.asStateFlow()

    // 녹음 파일 저장할 경로
    private val _recordedFilePath = MutableStateFlow<String?>(null)
    override val recordedFilePath: StateFlow<String?> = _recordedFilePath.asStateFlow()

    private var recordingJob: Job? = null
    // 다음 페이지 메서드
    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }
    // 녹음 시작 메서드
    override fun startRecording() {
        _secondsState.value = 0
        _recordingState.value = RecordingState.RECORDING

        // ⭐️ 실제 녹음 시작
        val fileName = "record_${System.currentTimeMillis()}.m4a"
        audioRecorder.startRecording(fileName)


        runRecordingTimer()
    }

    // 타이머 시작 메서드
    override fun runRecordingTimer() {
        // 기존 Job이 있다면 취소
        recordingJob?.cancel()
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                _secondsState.value++
                if (_secondsState.value >= 15) {
                    stopRecording()
                    break // 15초가 되면 루프를 종료
                }
            }
        }
    }

    // 녹음 정지, 녹음 완료
    override fun stopRecording() {
        //  타이머 취소
        recordingJob?.cancel()

        //  실제 녹음 중지
        audioRecorder.stopRecording()

        //  녹음이 끝난 후 파일 경로를 저장
        _recordedFilePath.value = audioRecorder.getFilePath()
        _recordingState.value = RecordingState.FINISHED
    }


}