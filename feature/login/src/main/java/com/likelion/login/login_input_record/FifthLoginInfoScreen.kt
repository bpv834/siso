package com.likelion.login.login_input_record

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.likelion.ui.R
import com.likelion.ui.component.button.AudioRecordingButton
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.button.CommonDisableButton
import com.likelion.ui.component.color_circle.RecordingCircle
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import java.io.File
import java.io.FileOutputStream

@Composable
fun FifthLoginInfoScreen(
    viewModel: FifthLoginInfoScreenViewModelType,
    onNavigateNext: () -> Unit
) {
    // val context = LocalContext.current

    val filePath by viewModel.recordedFilePath.collectAsStateWithLifecycle()
    // 녹음 시간 상태 변수
    val minuteState by viewModel.secondsState.collectAsStateWithLifecycle()
    // 녹음 상태변수 (enum 상태값 받음)
    val isRecording by viewModel.recordingState.collectAsStateWithLifecycle()
    // 1. Lottie 파일을 불러와 composition 객체를 생성합니다.
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mic_wave))
    // 2. 애니메이션 재생 상태를 제어합니다.
    // isPlaying = true: 애니메이션을 재생합니다.
    // iterations: LottieConstants.IterateForever는 무한 반복을 의미합니다.
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )


    // 이 Composable이 화면에서 사라질 때 실행되는 블록
    DisposableEffect(Unit) {
        onDispose {
            // 전달받은 viewModel이 FakeViewModel인지 확인
            if (viewModel is FakeFifthLoginInfoScreenViewModel) {
                // FakeViewModel일 경우에만 clear() 메서드를 호출
                viewModel.clear()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SisoColorTokens.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // 이 부분을 추가
    ) {
        Spacer(Modifier.size(8.dp))
        AsyncImage(
            model = R.drawable.img_circle_var_login5,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(24.dp))
        Text(
            text = "내 목소리를 들려주세요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.Gray90
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "여러분의 진솔한 생각과 경험을 담아, 상대방이 \n" +
                    "당신을 더 잘 이해할 수 있도록 \n" +
                    "15초 이내의 간단한 인삿말을 담아보세요.",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // 이 부분을 추가
        ) {
            when (isRecording) {

                // 녹음 전
                RecordingState.IDLE -> {
                    Spacer(Modifier.size(97.dp))
                    AsyncImage(
                        model = com.likelion.ui.R.drawable.img_mic,
                        contentDescription = "",
                        modifier = Modifier.size(98.dp)
                    )
                    Spacer(Modifier.size(40.dp))
                    Text(
                        text = "00:00",
                        style = SisoTypoTokens.Body1,
                        color = SisoColorTokens.Gray90
                    )
                    Spacer(Modifier.size(74.dp))
                    AudioRecordingButton {
                        viewModel.startRecording()
                    }
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = "건너뛰기",
                        style = SisoTypoTokens.Button2,
                        color = SisoColorTokens.Gray50,
                        modifier = Modifier.clickable {
                            onNavigateNext()
                        }
                    )
                }
                // 녹음중
                RecordingState.RECORDING -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.size(81.dp))
                        Box(
                            modifier = Modifier.size(130.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Lottie Animation
                            LottieAnimation(
                                composition = composition,
                                progress = { progress },
                                modifier = Modifier
                                    // graphicsLayer =렌더링 시점에서 확대/축소
                                    // 따라서 버튼이나 다른 위젯이 밀리지않음
                                    .graphicsLayer {
                                        val scale = 2.0f  // 최대 원 크기 기준
                                        scaleX = scale
                                        scaleY = scale
                                    }
                            )
                            AsyncImage(
                                model = R.drawable.ic_mic_stop,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        viewModel.stopRecording()
                                    }
                            )
                        }
                        Spacer(Modifier.size(24.dp))

                        Text(
                            text = "00:${minuteState.toString().padStart(2, '0')}",
                            style = SisoTypoTokens.Body1,
                            color = SisoColorTokens.Gray90
                        )
                        Spacer(Modifier.size(74.dp))
                        CommonDisableButton(text = "완료하기", onClick = {

                        })
                    }
                }
                // 녹음 완료
                else -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.size(97.dp))
                        // Lottie Animation
                        RecordingCircle(
                            onClickIcon = {},
                            iconWidget = {
                                AsyncImage(
                                    model = R.drawable.ic_mic_start,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            viewModel.playAudio(filePath ?: "")
                                        }
                                )
                            })
                        Spacer(Modifier.size(40.dp))
                        Text(
                            text = "00:${minuteState.toString().padStart(2, '0')}",
                            style = SisoTypoTokens.Body1,
                            color = SisoColorTokens.Gray90
                        )
                    }


                    Spacer(Modifier.size(43.dp))
                    CommonActiveButton(text = "완료하기", onClick = {
                        onNavigateNext()
           /*           val arr =  viewModel.getAudioBytes()
                        playAacFromByteArray(context = context, audioBytes =arr!!)*/
                    })
                    Spacer(Modifier.size(8.dp))
                    CommonActiveButton(text = "다시 녹음하기", onClick = { viewModel.startRecording() })
                    Spacer(Modifier.size(8.dp))
                }
            }
        }
        Spacer(Modifier.size(43.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun FifthLoginInfoScreenPreview() {
    SisoTheme {
        // Preview Context를 사용해 AudioRecorder를 생성
        val context = LocalContext.current
        val fakeAudioRecorder = AudioRecorderClass(context = context)
        val fakeViewModel = FakeFifthLoginInfoScreenViewModel(audioRecorder = fakeAudioRecorder)
        FifthLoginInfoScreen(viewModel = fakeViewModel, {})
    }
}

/**
 * 서버에서 받은 AAC(M4A) 바이트 배열을 재생
 * @param context: Context
 * @param audioBytes: 서버에서 받은 AAC(M4A) 오디오 바이트 배열
 */
fun playAacFromByteArray(context: Context, audioBytes: ByteArray) {
    try {
        // 임시 파일 생성
        val tempFile = File.createTempFile("temp_audio", ".m4a", context.cacheDir)
        FileOutputStream(tempFile).use { it.write(audioBytes) }

        // MediaPlayer 초기화 및 재생
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(tempFile.absolutePath)
            prepare() // 동기 준비
            start()   // 재생 시작

            setOnCompletionListener {
                it.release()          // 재생 끝나면 MediaPlayer 해제
                tempFile.delete()     // 임시 파일 삭제
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}