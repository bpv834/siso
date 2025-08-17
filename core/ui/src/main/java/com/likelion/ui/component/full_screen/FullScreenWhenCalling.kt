package com.likelion.ui.component.full_screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.model.UsersModel
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenWhenCalling(
    user: UsersModel,
    onClickKeepGoing: (UsersModel) -> Unit,
    onClickReport: (UsersModel) -> Unit,
    onClickCallEnd: () -> Unit,
    onClickMute: () -> Unit,
    onClickSpeaker: () -> Unit,
) {
    var isOpenBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.size(64.dp))
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "남은 통화시간 00:23",
                style = SisoTypoTokens.Body2,
                color = SisoColorTokens.Black
            )
        }
        Spacer(Modifier.size(49.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_end_call,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isOpenBottomSheet = true
                    },
                contentScale = ContentScale.FillWidth
            )
            Spacer(Modifier.size(8.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_mute,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .clickable {

                    },
                contentScale = ContentScale.FillWidth

            )
            Spacer(Modifier.size(8.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_speaker,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .clickable {

                    },
                contentScale = ContentScale.FillWidth

            )
        }
        Spacer(Modifier.size(54.dp))
    }
    if (isOpenBottomSheet) {
        ModalBottomSheet(
            // 닫힐 때 발생하는 이벤트를 알리는 메서드입니다. 직접 바텀시트를 닫는 메서드가 아닙니다.
            onDismissRequest = {
                isOpenBottomSheet = false
            },
            sheetState = sheetState
        ) {
            BottomSheetAfterCall(
                user = user,
                onClickReport = {},
                onClickKeepGoing = {},
                onDismissRequest = {
                    isOpenBottomSheet = false
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAfterCall(
    onClickKeepGoing: (UsersModel) -> Unit,
    onClickReport: (UsersModel) -> Unit,
    user: UsersModel,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 상단 헤더 (제목과 닫기 버튼)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {onDismissRequest()}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_24px),
                    contentDescription = "Close"
                )
            }
        }

        AsyncImage(
            model = user.userImages[0],
            contentDescription = null,
            contentScale = ContentScale.Crop, // 꽉 채워서 잘림
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )

        // 도움말 텍스트
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "${user.nickname}님과의 통화는 어땠나요?",
            style = SisoTypoTokens.Body2,
            color = SisoColorTokens.Black
        )
        Spacer(Modifier.size(14.dp))
        Text(
            text = "인연 이어가기를 누르면 \n" +
                    "메세지를 보내고 상대의 \n" +
                    "자세한 정보를 확인할 수 있어요",
            style = SisoTypoTokens.Body2,
            color = Color.Black
        )
        // 예시 이미지들
        Spacer(modifier = Modifier.height(54.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "신고하기",
                style = SisoTypoTokens.Button1,
                color = SisoColorTokens.GrayScale90,
                modifier = Modifier.clickable {
                    onClickReport(user)
                })
            Spacer(Modifier.size(32.dp))
            CommonActiveButton(
                text = "인연 이어가기",
                onClick = { onClickKeepGoing(user) },
                modifier = Modifier
                    .width(216.dp)
                    .height(54.dp)
            )
        }
    }
}

@Preview
@Composable
fun FullScreenWhenCallingPreview() {
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
        FullScreenWhenCalling(
            user = fakeUser,
            onClickKeepGoing = {},
            onClickReport = {},
            onClickCallEnd = {},
            onClickMute = {},
            onClickSpeaker = {})
    }
}

@Preview
@Composable
fun BottomSheetAfterCallPreview() {
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
        BottomSheetAfterCall(
            user = fakeUser,
            onClickKeepGoing = {},
            onClickReport = {},
            onDismissRequest = {}
        )
    }
}