package com.likelion.ui.component.full_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.component.button.CommonOutlinedButtonWithIconVertical
import com.likelion.ui.component.button.CustomOutlinedButton
import com.likelion.ui.component.button.CustomOutlinedButtonWithCustomContentColor
import com.likelion.ui.component.flow_row.CustomFlowTagRow
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FullScreenWhenCallActive(
    user: UserProfileModel,
    otherUser: UserProfileModel,
    callDuration: Int,
    onClickKeepGoing: (UsersModel) -> Unit,
    onClickCallEnd: () -> Unit,
    onClickMute: () -> Unit,
    onClickSpeaker: () -> Unit,
    isMute: Boolean,
    isSpeaker: Boolean,
    startCallTimer : ()-> Unit,
) {

    LaunchedEffect(Unit) {
        startCallTimer()
    }

    val min = callDuration / 60
    val sec = callDuration % 60
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.size(72.dp))
        // 프사, 닉네임, 나이, 지역 로우
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 사진
            AsyncImage(
                model = otherUser.profileImageUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.size(16.dp))
            // 닉, 나이,위치
            Column {
                // 닉네임
                Text(
                    text = otherUser.nickname,
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray90
                )
                Spacer(Modifier.size(4.dp))
                // 나이 위치
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${otherUser.age}세", style = SisoTypoTokens.Title3,
                        color = SisoColorTokens.Gray70
                    )
                    Spacer(Modifier.size(4.dp))
                    Icon(
                        painter = painterResource(id = com.likelion.ui.R.drawable.ic_location_on_24px),
                        contentDescription = "전화 버튼",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = otherUser.location,
                        style = SisoTypoTokens.SubTitle1,
                        color = SisoColorTokens.Gray90
                    )
                }
            }

        }
        Spacer(Modifier.size(165.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "남은시간",
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.Gray60
            )
            Text(
                text = String.format("%02d:%02d", min, sec),
                style = SisoTypoTokens.Title1,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(8.dp))
            CustomOutlinedButton(
                modifier = Modifier
                    .width(160.dp)
                    .height(54.dp),
                text = "통화 연장하기",
                onclickButton = {},
                isActive = if (callDuration < 60) true else false
            )
        }
        Spacer(Modifier.size(104.dp))
        // 공통 관심사 로우
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomOutlinedButton(
                text = "공통 관심사",
                isActive = false,
                modifier = Modifier
                    .height(35.dp),
                onclickButton = {},
                horizontalContentPadding = 10.dp
            )
            Spacer(Modifier.size(16.dp))
            val userInterests = user.interests
            val otherUserInterests = otherUser.interests
            val commonInterests = userInterests.intersect(otherUserInterests.toSet()).toList()
            CustomFlowTagRow(
                tags = commonInterests,
                maxLines = 2, modifier = Modifier,
                overflow = FlowRowOverflow.expandIndicator {
                    Text(text = "...")
                })

        }
        Spacer(Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            CommonOutlinedButtonWithIconVertical(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                icon = {
                    Icon(
                        painter = painterResource(com.likelion.ui.R.drawable.ic_call_end),
                        contentDescription = "",
                        tint = SisoColorTokens.Red60
                    )
                },
                onClick = {
                    onClickCallEnd()
                },
                text = "종료"
            )
            Spacer(Modifier.size(8.dp))
            val muteContentColor = if (isMute) SisoColorTokens.White else SisoColorTokens.Black
            val muteContainerColor = if (isMute) SisoColorTokens.Gray50 else SisoColorTokens.White

            CustomOutlinedButtonWithCustomContentColor(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentColor = muteContentColor,
                containerColor = muteContainerColor,
                icon = {
                    Icon(
                        painter = painterResource(com.likelion.ui.R.drawable.ic_mute),
                        contentDescription = "",
                    )
                },
                text = "음소거",
                onClick = {
                    onClickMute()
                }
            )
            Spacer(Modifier.size(8.dp))

            val speakContentColor = if (isSpeaker) SisoColorTokens.White else SisoColorTokens.Black
            val speakContainerColor =
                if (isSpeaker) SisoColorTokens.Blue50 else SisoColorTokens.White

            CustomOutlinedButtonWithCustomContentColor(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentColor = speakContentColor,
                containerColor = speakContainerColor,
                icon = {
                    Icon(
                        painter = painterResource(com.likelion.ui.R.drawable.ic_speaker),
                        contentDescription = "",
                    )
                },
                text = "스피커",
                onClick = { onClickSpeaker() }
            )

        }
        Spacer(Modifier.size(64.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenWhenCallActivePreview() {
    SisoTheme {
        val fakeUser = UserProfileModel(
            nickname = "닉네임은여덟글자",
            profileImageUrl = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
            age = 26,
            location = "경기 동두천시",
            interests = listOf("취미12")
        )

        val fakeUser2 = UserProfileModel(
            nickname = "닉네임은여덟글자",
            profileImageUrl = "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg",
            age = 26,
            location = "경기 동두천시",
            interests = listOf("취미12")
        )
        FullScreenWhenCallActive(
            user = fakeUser,
            otherUser = fakeUser2,
            onClickKeepGoing = {},
            onClickCallEnd = {},
            onClickMute = {},
            onClickSpeaker = {},
            isMute = true,
            isSpeaker = true,
            callDuration = 59,
            startCallTimer = {},
        )
    }
}