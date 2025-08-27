package com.likelion.home.mypage.record_edit_info_screen

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.home.mypage.isPlayableAudioUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordEditInfoScreenViewModel @Inject constructor(
    private val audioRecorder: AudioRecorderClass, // Hilt로 AudioRecorder 인스턴스를 주입받음
    // usecase
) : ViewModel(), RecordEditInfoScreenViewModelType {
    // 초기 상태는 녹음 전상태
    private val _recordingEditState = MutableStateFlow(RecordingEditState.IDLE)
    override val recordingEditState: StateFlow<RecordingEditState> = _recordingEditState.asStateFlow()

    // 초기 상태는 0 초
    private val _secondsState = MutableStateFlow(0)
    override val secondsState: StateFlow<Int> = _secondsState.asStateFlow()

    // 녹음 파일 저장할 경로
    private val _recordedFilePath = MutableStateFlow<String?>(null)
    override val recordedFilePath: StateFlow<String?> = _recordedFilePath.asStateFlow()

    // 서버에서 받은 음성 녹음 주소
    private val _receiverUrl = MutableStateFlow<String?>(null)
    override val receiverUrl: StateFlow<String?> = _receiverUrl.asStateFlow()

    private var recordingJob: Job? = null

    init {
        _receiverUrl.update { "" } // 주소를 받아옴
        val url = _receiverUrl.value!!
        if (url.isNotBlank()) {
            _recordingEditState.update { RecordingEditState.RE_EDIT } // 녹음 상태를 받아옴
        }else{
            _recordingEditState.update { RecordingEditState.IDLE } // 녹음 상태를 받아옴
        }
    }

    // 다음 페이지 메서드
    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }

    // 녹음 시작 메서드
    override fun startRecording() {
        _secondsState.value = 0
        _recordingEditState.value = RecordingEditState.RECORDING

        // ⭐️ 실제 녹음 시작
        val fileName = "record_${System.currentTimeMillis()}.m4a"
        audioRecorder.startRecording(fileName)

        runRecordingTimer()
    }

    // 타이머 시작 메서드
    override fun runRecordingTimer() {
        // 기존 Job이 있다면 취소
        recordingJob?.cancel()
        recordingJob = viewModelScope.launch {
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

        // ✨ 실제 오디오 녹음 중지 로직 대신 상태만 변경
        _recordingEditState.value = RecordingEditState.FINISHED
        //  실제 녹음 중지
        audioRecorder.stopRecording()

        //  녹음이 끝난 후 파일 경로를 저장
        _recordedFilePath.value = audioRecorder.getFilePath()
        _recordingEditState.value = RecordingEditState.FINISHED
    }

    // 녹음  재시작


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

    // 녹음본 저장 경로에서 파일을 가져와 byte 배열로 변환하는 메서드
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
            Timber.d("배열로 변환 d : ${file.readBytes().joinToString(" ")}")
            Timber.i("배열로 변환 i: ${file.readBytes()}")
            Timber.e("배열로 변환 e: ${file.readBytes()}")

            file.readBytes()
        } catch (e: Exception) {
            Timber.e("배열로 변환 에러: $e")
            null
        }
    }

}