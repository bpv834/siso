package com.likelion.ui.component.card

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonOutlinedButtonWithIconVertical
import com.likelion.ui.component.button.CustomButtonWithIcon
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UserCard(
    user: UsersModel,
    onImageClick: (imageUrl: String) -> Unit,
    onClickButtonCall: (receiverId: Long) -> Unit, // 상대 유저 uid를 얻어오는 메서드
    toCallScreen: (Long) -> Unit,
    isPossibleMessage: Boolean,
) {

    val context = LocalContext.current // Toast 메시지를 띄우기 위한 Context

    // ⭐️ Accompanist Permissions - RECORD_AUDIO 권한 상태 관리
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)


    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // 배경 투명
            , contentColor = Color.Unspecified  // 내용물 색상 유지
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(73.dp))
            // 온라인 상태
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            if (user.isOnline) SisoColorTokens.Green60 else Color.Gray,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (user.isOnline) "온라인" else "오프라인",
                    fontSize = 14.sp,
                    style = SisoTypoTokens.Label1,
                    color = SisoColorTokens.Gray90
                )
            }
            Spacer(Modifier.size(19.dp))

            // 사용자 사진 목록
            if (user.userImages.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(user.userImages) { index, imageUrl ->
                        Box {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(242.dp)
                                    .clickable { onImageClick(imageUrl) }
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 16.dp, bottom = 16.dp)
                                    .background(
                                        color = SisoColorTokens.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(99.dp)
                                    )
                                    .width(46.dp)
                                    .height(31.dp)
                            ) {
                                Text(
                                    text = "${index + 1}/${user.userImages.size}", // ← 인덱스 활용
                                    style = SisoTypoTokens.Label1,
                                    color = SisoColorTokens.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.size(16.dp))


            // 위치
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .height(23.dp)
            ) {
                AsyncImage(
                    model = R.drawable.ic_location_on_24px,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "위치 : ${user.location}",
                    style = SisoTypoTokens.Label1,
                    color = SisoColorTokens.Gray90
                )
            }
            Spacer(Modifier.size(12.dp))
            // 닉네임 및 나이

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = "${user.nickname}, ",
                        style = SisoTypoTokens.Title2,
                        color = SisoColorTokens.Gray90
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = "${user.age}세",
                        style = SisoTypoTokens.Title2,
                        color = SisoColorTokens.Gray60
                    )
                }
                AsyncImage(
                    model = R.drawable.ic_voicesample,
                    contentDescription = "",
                    modifier = Modifier
                        .width(130.dp)
                        .height(44.dp)
                )
            }


            Spacer(Modifier.size(12.dp))
            // 관심사 목록
            Text(
                text = user.interests.joinToString(separator = " #", prefix = "#"),
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(12.dp))
            // 자기소개
            Text(
                text = user.introduce,
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray90, modifier = Modifier.height(54.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.size(15.dp))

            Row(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            if (isPossibleMessage) SisoColorTokens.Blue50
                            else SisoColorTokens.Gray40
                        ),
                    // Box 배경색을 파란색으로 설정
                    contentAlignment = Alignment.Center // Box 내부의 콘텐츠를 정중앙에 배치
                ) {
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(80.dp)
                            .height(80.dp),
                        // IconButton 크기 설정,
                        onClick = {
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            // `isPossibleMessage` 상태에 따라 아이콘 색상을 변경합니다.
                            contentColor = SisoColorTokens.White
                        ),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_message),
                                contentDescription = "메시지 보내기",
                            )
                        }
                    )
                }

                Spacer(Modifier.size(12.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .weight(1f)
                        .background(
                            SisoColorTokens.Green60
                        ),
                    contentAlignment = Alignment.Center // Box 내부의 콘텐츠를 정중앙에 배치
                ) {
                    IconButton(
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        // IconButton 크기 설정,
                        onClick = {
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            // `isPossibleMessage` 상태에 따라 아이콘 색상을 변경합니다.
                            contentColor = SisoColorTokens.White
                        ),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_call),
                                contentDescription = "메시지 보내기",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    val sampleUser = UsersModel(
        id = 1,
        isOnline = true,
        userImages = listOf("https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"),
        location = "서울 강남구",
        nickname = "강남멋쟁이",
        age = 30,
        voiceUrl = "voice_url",
        interests = listOf("독서", "영화", "헬스"),
        introduce = "안녕하세요. 자기소개입니다. 저는 영화와 독서를 좋아합니다."
    )
    UserCard(
        user = sampleUser,
        {},
        {},
        { id1,  -> },
        false
    )
}