package com.likelion.ui.component.full_screen

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.call_for_caller.model.UsersModel
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.button.CommonOutlinedButtonWithIconVertical
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun FullScreenCallingTry(otherUser: UsersModel, onClickButtonCallEnd: () -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(Modifier.size(160.dp))
        Text(
            text = "${otherUser.nickname} 님과\n연결중이에요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.Gray90
        )
        Spacer(Modifier.size(22.dp))
        AsyncImage(
            model = otherUser.userImages[0], contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(160.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.size(32.dp))

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
                            color = SisoColorTokens.Gray70
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
                            text = "\n서로 다른 점보다는 공감할 수\n있는 이야기를 먼저 나눠요.",
                            textAlign = TextAlign.Center,
                            style = SisoTypoTokens.Body4,
                            color = SisoColorTokens.Gray70
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
                            color = SisoColorTokens.Gray70
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
        Spacer(Modifier.size(68.dp))
        CommonOutlinedButtonWithIconVertical(
            icon = {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.ic_call_end,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onClickButtonCallEnd() }
                )
            },
            text = "전화끊기",
            onClick = { onClickButtonCallEnd() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(96.dp)
        )
        CommonActiveButton(
            modifier = Modifier.fillMaxSize().height(54.dp),
            text = "임시 채팅방 생성", onClick = {

            })
    }

}


@Preview(showBackground = true)
@Composable
fun FullScreenCallingTryPreview() {
    val fakeUser = UsersModel(
        id = 4L,
        isOnline = true,
        userImages = listOf(
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
    FullScreenCallingTry(fakeUser, {})
}