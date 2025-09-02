package com.likelion.ui.component.full_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun FullScreenWhenCalled(user: UsersModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(SisoColorTokens.Gray5),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(99.dp))
        Text(
            text = "${user.nickname}님으로부터\n전화가 걸려왔어요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.Gray90
        )
        Spacer(Modifier.size(24.dp))
        AsyncImage(
            model = user.userImages[0],
            contentDescription = null,
            contentScale = ContentScale.Crop, // 꽉 채워서 잘림
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.size(14.dp))
        Row {
            Text(
                text = user.nickname,
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = "${user.age}세",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray50
            )
        }
        Spacer(Modifier.size(8.dp))
        Row(modifier = Modifier.height(29.dp)) {
            Icon(
                painter = painterResource(id = com.likelion.ui.R.drawable.ic_location_on_24px),
                contentDescription = "위치 아이콘",
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = user.location,
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray90
            )
        }
        Spacer(Modifier.size(8.dp))
        Text(
            "${user.interests.joinToString(prefix = "#", separator = "# ")}",
            style = SisoTypoTokens.Label1,
            color = SisoColorTokens.Gray70
        )
        Spacer(Modifier.size(36.dp))
        Text(
            text = user.introduce,
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray90,
            modifier = Modifier.height(80.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis

        )
        Spacer(Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_call_later,
                contentDescription = "",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.size(16.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_call_accept,
                contentDescription = "",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillBounds

            )
        }
    }
}

@Preview
@Composable
fun FullScreenWhenCalledPreview() {
    SisoTheme {
        val fakeUser = UsersModel(
            id = 4L,
            userImages = listOf(),
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("풋볼", "영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다.",
            presentStatus = com.likelion.domain.enums.PresentStatus.IN_CALL


        )
        FullScreenWhenCalled(user = fakeUser)
    }
}
