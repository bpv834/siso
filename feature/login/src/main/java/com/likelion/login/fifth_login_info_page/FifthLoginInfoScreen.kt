package com.likelion.login.fifth_login_info_page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.color_circle.RecordingCircle
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun LastLoginInfoScreen(viewModel: FifthLoginInfoScreenViewModelType) {

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
        Text("내 목소리를 들려주세요", style = SisoTypoTokens.Title2, color = SisoColorTokens.GrayScale90)
        Spacer(Modifier.size(8.dp))
        Text(
            "여러분의 진솔한 생각과 경험을 담아, 상대방이 \n" +
                    "당신을 더 잘 이해할 수 있도록 \n" +
                    "15초 이내의 간단한 인삿말을 담아보세요.",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.GrayScale60
        )
        Spacer(Modifier.size(113.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // 이 부분을 추가
        ) {
            when (isRecording) {
                // 녹음 전
                RecordingState.IDLE -> {
                    AsyncImage(
                        model = com.likelion.ui.R.drawable.img_mic,
                        contentDescription = "",
                        modifier = Modifier.size(98.dp)
                    )
                    Spacer(Modifier.size(24.dp))
                    Text(
                        "00:00",
                        style = SisoTypoTokens.Body1,
                        color = SisoColorTokens.GrayScale90
                    )
                    Spacer(Modifier.size(158.dp))
                    CommonActiveButton("녹음시작", { viewModel.startRecording() })
                    Spacer(Modifier.size(8.dp))
                    Text(
                        "건너뛰기",
                        style = SisoTypoTokens.Button2,
                        color = SisoColorTokens.GrayScale50
                    )
                }
                // 녹음중
                RecordingState.RECORDING -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(98.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Lottie Animation
                            LottieAnimation(
                                composition = composition,
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxSize()
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
                                    .size(24.dp)
                                    .clickable {
                                        viewModel.stopRecording()
                                    }
                            )
                            Spacer(Modifier.size(8.dp))
                        }
                        Text(
                            text = "00:${minuteState.toString().padStart(2, '0')}",
                            style = SisoTypoTokens.Body1,
                            color = SisoColorTokens.GrayScale90
                        )
                        Spacer(Modifier.size(158.dp))
                    }
                }
                // 녹음 완료
                else -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Lottie Animation
                        RecordingCircle() {
                            AsyncImage(
                                model = R.drawable.ic_mic_start,
                                contentDescription = "", modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(Modifier.size(8.dp))
                    }
                    Text(
                        text = "00:${minuteState.toString().padStart(2, '0')}",
                        style = SisoTypoTokens.Body1,
                        color = SisoColorTokens.GrayScale90
                    )

                    Spacer(Modifier.size(158.dp))
                    CommonActiveButton("완료하기", {})
                    Spacer(Modifier.size(8.dp))
                    CommonActiveButton("다시 녹음하기", { viewModel.startRecording() })
                }
            }
        }
        Spacer(Modifier.size(43.dp))
    }

}

//@Composable
//fun LottieAnimation(composition: ERROR, progress: () -> ERROR, modifier: Modifier) {
//    TODO("Not yet implemented")
//}


@Preview(showBackground = true)
@Composable
fun LastLoginInfoScreenPreview() {
    SisoTheme {
        // Preview Context를 사용해 AudioRecorder를 생성
        val context = LocalContext.current
        val fakeAudioRecorder = AudioRecorderClass(context)
        val fakeViewModel = FakeFifthLoginInfoScreenViewModel(fakeAudioRecorder)
        LastLoginInfoScreen(fakeViewModel)
    }
}
