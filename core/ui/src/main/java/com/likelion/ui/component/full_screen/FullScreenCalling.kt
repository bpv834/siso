package com.likelion.ui.component.full_screen

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import org.intellij.lang.annotations.JdkConstants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FullScreenCalling(caller: UsersModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(160.dp))
        Text(text = "${caller.nickname}님과\n연결중이에요", style = SisoTypoTokens.Title2)
        Spacer(Modifier.size(16.dp))
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
                    color = SisoColorTokens.GrayScale90
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "${caller.age}세",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.GrayScale60
                )
            }
            Spacer(Modifier.size(8.dp))
            // 위치 아이콘 + 지역명
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.ic_location_on_24px,
                    contentDescription = "", modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    caller.location,
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.GrayScale90
                )
                Spacer(Modifier.size(14.dp))
            }
            Spacer(Modifier.size(14.dp))

            // 공통관심사
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(64.dp)
            ) {
                CommonActiveButton(
                    text = "공통관심사",
                    onClick = {},
                    modifier = Modifier
                        .height(35.dp)
                        .wrapContentSize(),

                    )
                Spacer(Modifier.size(8.dp))
                FlowRow(
                    maxLines = 2,
                    overflow = FlowRowOverflow.expandIndicator {
                        Text(text = "...")
                    }
                ) {
                    caller.interests.forEach { tag ->
                        Text(
                            text = "#$tag",
                            style = SisoTypoTokens.Button1,
                            color = SisoColorTokens.GrayScale90
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Spacer(Modifier.size(24.dp))

            }
            Text(
                text = caller.introduce,
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale90,
                modifier = Modifier.height(80.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenCallingPreview() {
    SisoTheme {
        val caller = UsersModel(
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
        FullScreenCalling(caller = caller)
    }
}