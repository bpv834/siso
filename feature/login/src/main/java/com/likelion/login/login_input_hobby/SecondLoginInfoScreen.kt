package com.likelion.login.login_input_hobby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonButtonWithState
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.component.text_button.CommonTextButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens


@Composable
fun SecondLoginInfoScreen(
    viewModel: SecondLoginInfoScreenViewModelType,  // 기본값 제거
    onNavigateNext: () -> Unit,
) {
    val selectedInterests by viewModel.selectedInterests.collectAsStateWithLifecycle()
    val isPossibleNextState by viewModel.isPossibleNextState.collectAsStateWithLifecycle()
    val interests = mapOf(
        "문화 & 예술" to listOf("음악감상", "사진촬영", "서예", "글쓰기", "악기연주", "노래부르기", "댄스"),
        "운동 & 야외활동" to listOf("등산", "낚시", "골프", "자전거 타기", "캠핑", "수영"),
        "여가 & 취미" to listOf("독서", "여행", "뜨개질", "맛집", "명상", "와인 감상", "인테리어")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // 이 부분을 추가
    ) {
        Spacer(modifier = Modifier.size(size = 8.dp))

        // Step indicator
        AsyncImage(
            model = R.drawable.img_circle_bar_login2,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(
            text = "나의 관심을 선택해주세요", style = SisoTypoTokens.Title2
        )
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text(
            text = "최소 3개 이상 선택해주세요\n많이 고를수록 매칭 확률이 높아져요\n정보는 나중에 수정할 수 있어요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.GrayScale60
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(
            text = "문화&예술", style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        ChipRow(
            chips = interests["문화 & 예술"]!!,
            onClick = { chipText -> // 람다의 인자로 클릭된 텍스트를 받음
                viewModel.onClickToggle(chipText) // 받은 텍스트를 viewModel 함수에 전달
            }, selectedInterests = selectedInterests
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        Text(text = "운동 & 야외활동", style = SisoTypoTokens.SubTitle1, color = SisoColorTokens.GrayScale50)
        Spacer(modifier = Modifier.size(size = 12.dp))
        ChipRow(
            chips = interests["운동 & 야외활동"]!!,
            onClick = { chipText -> // 람다의 인자로 클릭된 텍스트를 받음
                viewModel.onClickToggle(chipText) // 받은 텍스트를 viewModel 함수에 전달
            }, selectedInterests = selectedInterests
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        Text(
            text = "여가 & 취미",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        ChipRow(
            chips = interests["여가 & 취미"]!!,
            onClick = { chipText -> // 람다의 인자로 클릭된 텍스트를 받음
                viewModel.onClickToggle(chipText) // 받은 텍스트를 viewModel 함수에 전달
            }, selectedInterests = selectedInterests
        )
        Spacer(modifier = Modifier.size(size = 43.dp))
        CommonButtonWithState(text = "계속하기", onClick = { onNavigateNext() }, isPossibleNextState)
        Spacer(Modifier.size(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTextButton(
                text = "건너뛰기",
                style = SisoTypoTokens.Button2,
                color = SisoColorTokens.GrayScale50,
                onClick = { onNavigateNext() })
        }
        Spacer(Modifier.size(39.dp))


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipRow(
    chips: List<String>,
    selectedInterests: Set<String>,
    onClick: (String) -> Unit,

    ) {
    FlowRow(
        modifier = Modifier.padding(0.dp),
        maxItemsInEachRow = 3,     // 한 줄에 최대 3개
        // 가로 간격을 8.dp로 설정
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        // 세로 간격을 8.dp로 설정
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        chips.forEach { chipText ->
            CommonChip(
                text = chipText,
                isSelected = selectedInterests.contains(chipText),
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SecondLoginInfoScreenPreview() {
    SecondLoginInfoScreen(
        viewModel = FakeSecondLoginInfoScreenViewModel(), {}
    )
}

