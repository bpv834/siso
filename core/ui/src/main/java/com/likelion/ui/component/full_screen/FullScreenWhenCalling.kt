package com.likelion.ui.component.full_screen

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.domain.model.UsersModel
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun FullScreenWhenCalling(user: UsersModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {

        // 사용자 사진 목록
        if (user.userImages.isNotEmpty()) {
            LazyRow(
            ) {
                items(user.userImages) { imageUrl ->
                    Box {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "",
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(242.dp)
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop // ⭐️ 비율을 유지하며 공간을 채움
                        )
                        Box(
                            modifier = Modifier
                                .width(46.dp)
                                .height(31.dp)
                                .padding(end = 16.dp, bottom = 16.dp)
                                .background(
                                    color = SisoColorTokens.Black.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(99.dp)
                                )
                                .align(Alignment.BottomEnd)
                        ) {
                            Text(
                                text = "3/2",
                                style = SisoTypoTokens.Label1,
                                color = SisoColorTokens.White,
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.size(24.dp))

        // 닉네임 및 나이
       Row {
           Text(
               text = user.nickname,
               style = SisoTypoTokens.Title2, color = SisoColorTokens.GrayScale90
           )
           Spacer(Modifier.size(8.dp))
           Text(
               text = "${user.age}세",
               style = SisoTypoTokens.Title2, color = SisoColorTokens.GrayScale50
           )

       }
        Spacer(Modifier.size(8.dp))

        // 위치
        Row(modifier = Modifier.height(23.dp)) {
            Icon(
                painter = painterResource(id = com.likelion.ui.R.drawable.ic_location_on_24px),
                contentDescription = "위치 아이콘",
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = user.location,
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.GrayScale90
            )
        }
        Spacer(Modifier.size(8.dp))
        // 관심사 목록
        Text(
            text = user.interests.joinToString(separator = " #", prefix = "#"),
            style = SisoTypoTokens.Label1,
            color = SisoColorTokens.GrayScale70
        )
        Spacer(Modifier.size(8.dp))
        // 자기소개
        Text(
            text = user.introduce,
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.GrayScale90,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.height(90.dp)
        )
        Spacer(Modifier.size(72.dp))
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            Text(text = "남은 통화시간 00:23", style = SisoTypoTokens.Body2, color = SisoColorTokens.Black)
        }
        Spacer(Modifier.size(49.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth().height(96.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_end_call,
                contentDescription = "",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillWidth
            )
            Spacer(Modifier.size(8.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_mute,
                contentDescription = "",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillWidth

            )
            Spacer(Modifier.size(8.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_speaker,
                contentDescription = "",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillWidth

            )
        }
        Spacer(Modifier.size(54.dp))
    }
}

@Preview
@Composable
fun FullScreenWhenCallingPreview(){
    SisoTheme {
        val fakeUser = UsersModel(
            id = 4L,
            isOnline = true,
            userImages = listOf(
                "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
            ),
            location = "America",
            nickname = "코딩러",
            age = 65,
            voiceUrl = "https://example.com/voice1.mp3",
            interests = listOf("풋볼", "영화", "음악"),
            introduce = "안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 / 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다 /" +
                    " 안녕하세요. 코딩을 좋아하는 개발자입니다."
        )
        FullScreenWhenCalling(fakeUser)
    }
}