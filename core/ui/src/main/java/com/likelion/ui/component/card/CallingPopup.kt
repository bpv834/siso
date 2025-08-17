package com.likelion.ui.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.model.UsersModel
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun CallingPopup(user: UsersModel) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )

    Card(
        modifier = Modifier
            .padding(horizontal = 55.dp)
            .width(283.dp)
            .height(474.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), // ✅ The column now fills the card
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(59.dp))
            Text(
                text = "${user.nickname} 님과\n연결중이에요",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.GrayScale90
            )
            Spacer(Modifier.size(65.dp))
            AsyncImage(
                model = R.drawable.img_calling_wave, contentDescription = "",
                modifier = Modifier
                    .width(174.dp)
                    .height(60.dp)
            )
            Spacer(Modifier.size(56.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "처음엔 \n" +
                                        "가볍고 따뜻한 이야기로 \n" +
                                        "시작해보세요.",
                                textAlign = TextAlign.Center,
                                style = SisoTypoTokens.Body4,
                                color = SisoColorTokens.GrayScale70
                            )
                            Spacer(Modifier.size(56.dp))
                            AsyncImage(
                                model = R.drawable.img_indicator1,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(46.dp)
                                    .height(10.dp)
                            )
                        }
                    }

                    1 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "서로 다른 점보다는 공감할 수\n있는 이야기를 먼저 나눠요.",
                                textAlign = TextAlign.Center,
                                style = SisoTypoTokens.Body4,
                                color = SisoColorTokens.GrayScale70
                            )
                            Spacer(Modifier.size(69.dp))
                            AsyncImage(
                                model = R.drawable.img_indicator2,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(46.dp)
                                    .height(10.dp)
                            )
                        }
                    }

                    2 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "처음엔 \n" +
                                        "가볍고 따뜻한 이야기로 \n" +
                                        "시작해보세요.",
                                textAlign = TextAlign.Center,
                                style = SisoTypoTokens.Body4,
                                color = SisoColorTokens.GrayScale70
                            )
                            Spacer(Modifier.size(56.dp))
                            AsyncImage(
                                model = R.drawable.img_indicator3,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(46.dp)
                                    .height(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CallingPopupPreview() {
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
    CallingPopup(fakeUser)
}