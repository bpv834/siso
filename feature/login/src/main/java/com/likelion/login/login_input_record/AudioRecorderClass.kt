package com.likelion.login.login_input_record

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AudioRecorderClass @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null

    // 녹음 시작
    fun startRecording(fileName: String) {
        val outputDir = context.cacheDir // 캐시 저장
        outputFile = File(outputDir, fileName)

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 (API 31) 이상
            MediaRecorder(context)
        } else {
            // Android 12 (API 31) 미만
            @Suppress("deprecation")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(64000)
            setOutputFile(outputFile?.absolutePath)

            try {
                prepare()
                start()
            } catch (e: IOException) {
                // 녹음 시작 실패 처리
                e.printStackTrace()
            }
        }
    }

    // 녹음 중지
    fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
                release()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
        mediaRecorder = null
    }

    // 리소스 정리 (stopRecording()이 호출된 이후, 명시적으로 리소스를 해제해야 할 때 사용)
    fun releaseRecorder() {
        mediaRecorder?.release()
        mediaRecorder = null
    }

    // 녹음된 파일 경로 반환
    fun getFilePath(): String? = outputFile?.absolutePath
}