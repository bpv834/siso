package com.likelion.ui.component.full_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FullScreenCallEnd(
    otherUser: com.likelion.domain.call_for_caller.model.UsersModel,
    onClickBackButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(Modifier.size(48.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(com.likelion.ui.R.drawable.ic_close_24px),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onClickBackButton()
                }
            )
        }
        Spacer(Modifier.size(31.dp))
        // 프로필 사진
        AsyncImage(
            model = otherUser.userImages[0],
            contentDescription = "",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(22.dp))
        // 나이~ 소개
        Column(
            modifier = Modifier.padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 닉네임 + 나이
            Row {
                Text(
                    text = "${otherUser.nickname},",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray90
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "${otherUser.age}세",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray60
                )
            }
            Spacer(Modifier.size(80.dp))
            Text(
                text = "인연 이어가기를 누르면 \n" +
                        "메세지를 보내고 상대의 \n" +
                        "자세한 정보를 확인할 수 있어요",
                style = SisoTypoTokens.Body2,
                color = SisoColorTokens.Gray90,
                modifier = Modifier.fillMaxWidth(), // 가로 길이를 최대로 늘립니다.
                textAlign = TextAlign.Center // 텍스트를 가운데로 정렬합니다.
            )
            //
            Spacer(Modifier.size(158.dp))

            CommonActiveButton(
                text = "인연 이어가기",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            )


        }

    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenCallEndPreview() {
    SisoTheme {
        val caller = com.likelion.domain.call_for_caller.model.UsersModel(
            id = 4L,
            userImages = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
            location = "America",
            nickname = "여덟글자닉네임자",
            age = 65,
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
        )
        FullScreenCallEnd(otherUser = caller, onClickBackButton = {})
    }
}