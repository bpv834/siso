package com.likelion.login.login_input_record

import kotlinx.coroutines.flow.StateFlow

interface FifthLoginInfoScreenViewModelType {

    val recordingState: StateFlow<RecordingState> // 녹음 상태를 노출하는 StateFlow 추가
    val secondsState : StateFlow<Int> // 초 상태 변수
    val recordedFilePath: StateFlow<String?> // 녹음 파일 경로

    fun onClickNextButton() // 다음 클릭 메서드
    fun startRecording() // 녹음 시작 메서드
    fun stopRecording() // 녹음 중지 메서드
    fun runRecordingTimer() // 타이머 시작 메서드
    fun playAudio(filePath: String) // 녹음 시작 메서드
    fun getAudioBytes(): ByteArray? // 녹음된 파일 읽는 메서드
}