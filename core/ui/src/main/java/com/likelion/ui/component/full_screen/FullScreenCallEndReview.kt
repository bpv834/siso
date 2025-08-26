package com.likelion.ui.component.full_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.component.button.CommonOutlinedButtonWithIconVertical
import com.likelion.ui.component.text_button.CommonTextButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun FullScreenCallEndReview(
    caller: com.likelion.domain.call_for_caller.model.UsersModel,
    onClickReport: () -> Unit,
    onClickAnother: () -> Unit,
    onClickKeepGoing: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(120.dp))
        // 프로필 사진
        AsyncImage(
            model = caller.userImages[0],
            contentDescription = "",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(24.dp))
        // 나이~ 소개
        Column(
            modifier = Modifier.padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 닉네임 + 나이
            Row {
                Text(
                    text = caller.nickname,
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray90
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "${caller.age}세",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray60
                )
            }
            Spacer(Modifier.size(66.dp))
            Text(
                text = "${caller.nickname}님과의\n통화는 어땠나요?",
                style = SisoTypoTokens.Body2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(84.dp))
            Row {
                CommonOutlinedButtonWithIconVertical(
                    icon = {
                        AsyncImage(
                            model = com.likelion.ui.R.drawable.ic_heart_break,
                            contentDescription = "",
                            modifier = Modifier.size(45.dp)
                        )
                    },
                    modifier = Modifier
                        .size(142.dp)
                        .safeContentPadding(),
                    text = "고민할래요",
                    onClick = {}
                )
                Spacer(Modifier.size(8.dp))
                CommonOutlinedButtonWithIconVertical(
                    icon = {
                        AsyncImage(
                            model = com.likelion.ui.R.drawable.ic_heart,
                            contentDescription = "",
                            modifier = Modifier.size(45.dp)
                        )
                    },
                    modifier = Modifier
                        .size(142.dp)
                        .safeContentPadding(),
                    text = "연락할래요",
                    onClick = {}
                )
            }
            Spacer(Modifier.size(30.dp))
            CommonTextButton(
                "신고하기",
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.Gray60,
                onClick = {
                    onClickReport()
                },
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenCallReviewPreview() {
    SisoTheme {
        val caller = com.likelion.domain.call_for_caller.model.UsersModel(
            id = 4L,
            isOnline = true,
            userImages = listOf(
                "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
            ),
            location = "America",
            nickname = "여덟글자닉네임자",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf(
                "풋볼",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
                "영222화",
            ),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다."
        )
        FullScreenCallEndReview(caller = caller, {}, {}, {})
    }
}