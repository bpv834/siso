package com.likelion.login.last_login_info_page

import kotlinx.coroutines.flow.StateFlow

interface LastLoginInfoScreenViewModelType {

    val recordingState: StateFlow<RecordingState> // 녹음 상태를 노출하는 StateFlow 추가
    val minuteState : StateFlow<Int> // 시간 상태 변수
    fun onClickNextButton() // 다음 클릭 메서드
    fun startRecording() // 녹음 시작 메서드
    fun stopRecording() // 녹음 중지 메서드
    fun runRecordingTimer() // 타이머 시작 메서드
}