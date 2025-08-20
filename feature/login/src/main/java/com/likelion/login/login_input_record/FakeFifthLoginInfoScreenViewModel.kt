package com.likelion.login.login_input_record

import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

class FakeFifthLoginInfoScreenViewModel(
    // usecase
    private val audioRecorder: AudioRecorderManager // ✨ FakeViewModel도 AudioRecorder를 받음
) : FifthLoginInfoScreenViewModelType {

    // 1. 뷰모델의 수명주기에 맞춰 하나의 CoroutineScope를 생성합니다.
    // + 연산자는 두 개의 CoroutineContext 요소를 결합하는 역할을 합니다.
    // 새로운 독립적인 Job을 생명주기 관리자로 사용하고, 코루틴은 메인 스레드에서 실행되도록 설정된 CoroutineScope"를 생성하겠다는 의미
    private val fakeViewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    // 현재 녹음 상태
    private val _recordingState = MutableStateFlow(RecordingState.IDLE)
    override val recordingState: StateFlow<RecordingState> =_recordingState.asStateFlow()
    // 시간 상태
    private val _secondsState = MutableStateFlow(0)
    override val secondsState: StateFlow<Int> = _secondsState.asStateFlow()

    // 녹음 파일 저장할 경로
    private val _recordedFilePath = MutableStateFlow<String?>(null)
    override val recordedFilePath: StateFlow<String?> = _recordedFilePath.asStateFlow()

    // 코루틴 관리 작업
    private var timerJob: Job? = null

    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }
    // 녹음 시작 메서드
    override fun startRecording() {
        // ✨ 실제 오디오 녹음 로직 대신, 상태만 변경
        _secondsState.value = 0
        _recordingState.value = RecordingState.RECORDING
        _recordedFilePath.value = null // 새 녹음 시작 시 경로 초기화
        runRecordingTimer()

        // ✨ 실제 AudioRecorder 객체의 startRecording()을 호출
        // val fileName = "record_preview_${System.currentTimeMillis()}.m4a"
        // audioRecorder.startRecording(fileName)
    }

    // 타이머 시작 메서드
    override fun runRecordingTimer() {
        timerJob?.cancel()
        // 2. 미리 생성한 fakeViewModelScope를 사용해 코루틴을 실행합니다.
        timerJob = fakeViewModelScope.launch {
            while (true) {
                delay(1000L)
                _secondsState.value++
                if(_secondsState.value >15) {
                    stopRecording()
                }
            }
        }
    }

    // 녹음 정지, 녹음 완료
    override fun stopRecording() {
        // ✨ 타이머 종료
        timerJob?.cancel()
        timerJob = null

        // ✨ 실제 오디오 녹음 중지 로직 대신 상태만 변경
        _recordingState.value = RecordingState.FINISHED

        // ✨ 실제 AudioRecorder 객체의 stopRecording()을 호출하고 경로를 가져옴
        // audioRecorder.stopRecording()
        // _recordedFilePath.value = audioRecorder.getFilePath()
    }


    // 3. FakeViewModel의 수명이 끝날 때 모든 코루틴을 취소하는 메서드를 추가합니다.
    fun clear() {
        fakeViewModelScope.cancel()
    }
    // 오디오 재생 로직을 담당하는 별도의 클래스나 파일
    override fun playAudio(filePath: String) {
        try {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare() // 파일을 불러올 준비를 합니다.
                start() // 재생 시작
            }
            // 재생이 끝나면 MediaPlayer 자원을 해제합니다.
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: Exception) {
            // 오류 처리
            e.printStackTrace()
        }
    }

    override  fun getAudioBytes(): ByteArray? {
        // recordedFilePath Flow에서 현재 값을 가져옵니다.
        val path = recordedFilePath.value ?: return null

        // 파일 경로가 유효한지 확인합니다.
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            // 파일이 존재하지 않거나 읽을 수 없으면 null 반환
            Timber.d("파일이 존재하지 않음: $path")
            return null
        }

        // 파일의 내용을 바이트 배열로 읽어옵니다.
        return try {
            Timber.e("배열로 변환 : ${file.readBytes()}")
            file.readBytes()
        } catch (e: Exception) {
            Timber.e("배열로 변환 에러: $e")
            null
        }
    }

}
