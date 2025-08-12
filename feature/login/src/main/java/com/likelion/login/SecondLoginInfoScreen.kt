package com.likelion.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens


@Composable
fun SecondLoginInfoScreen(
) {
    val interests = mapOf(
        "문화 & 예술" to listOf("음악감상", "사진촬영", "서예", "글쓰기", "악기연주", "노래부르기", "댄스"),
        "운동 & 야외활동" to listOf("등산", "낚시", "골프", "자전거 타기", "캠핑", "수영"),
        "여가 & 취미" to listOf("독서", "여행", "뜨개질", "맛집", "명상", "와인 감상", "인테리어")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(size = 8.dp))

        // Step indicator
        AsyncImage(
            model = com.likelion.ui.R.drawable.img_circle_bar_login2,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(
            "나의 관심을 선택해주세요", style = SisoTypoTokens.Title2
        )
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text(
            text = "최소 3개 이상 선택해주세요\n많이 고를수록 매칭 확률이 높아져요\n정보는 나중에 수정할 수 있어요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.GrayScale60
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(text = "문화&예술", style = SisoTypoTokens.SubTitle1)
        Spacer(modifier = Modifier.size(size = 12.dp))
        ChipRowExample(
            chips = interests["문화 & 예술"]!!,
            onClick = {}
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        Text(text = "운동 & 야외활동", style = SisoTypoTokens.SubTitle1)
        Spacer(modifier = Modifier.size(size = 12.dp))
        ChipRowExample(
            chips = interests["운동 & 야외활동"]!!,
            onClick = {}
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        Text(text = "여가 & 취미", style = SisoTypoTokens.SubTitle1)
        ChipRowExample(
            chips = interests["여가 & 취미"]!!,
            onClick = {}
        )
        Spacer(modifier = Modifier.size(size = 6.dp))
        CommonActiveButton("계속하기", modifier = Modifier.fillMaxWidth(), onClick = {})


    }
}

@Composable
fun ChipRowExample(chips: List<String>, onClick: (String) -> Unit) {
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
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SecondLoginInfoScreenPreview() {
    SecondLoginInfoScreen()
}

