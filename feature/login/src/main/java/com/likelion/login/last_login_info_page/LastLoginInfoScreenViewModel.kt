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
    // usecase
) : ViewModel(), LastLoginInfoScreenViewModelType {
    // 초기 상태는 녹음 전상태
    private val _recordingState = MutableStateFlow(RecordingState.IDLE)
    override val recordingState: StateFlow<RecordingState> = _recordingState.asStateFlow()

    // 초기 상태는 0 초
    private val _minuteState = MutableStateFlow(0)
    override val minuteState: StateFlow<Int> = _minuteState.asStateFlow()
    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }

    override fun startRecording() {
        _minuteState.value = 0
        _recordingState.value = RecordingState.RECORDING
        runRecordingTimer()
    }

    // 타이머 시작 메서드
    override fun runRecordingTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                _minuteState.value++
                if (_minuteState.value >= 15) {
                    stopRecording()
                }
            }
        }
    }

    // 녹음 정지, 녹음 완료
    override fun stopRecording() {
        _recordingState.value = RecordingState.FINISHED
    }


}