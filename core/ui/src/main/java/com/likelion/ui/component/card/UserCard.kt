package com.likelion.ui.component.card

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.domain.home.model.UsersModel
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun UserCard(
    user: UsersModel,
    onImageClick: (imageUrl: String) -> Unit,
) {
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
                    color = SisoColorTokens.GrayScale90
                )
            }
            Spacer(Modifier.size(19.dp))

            // 사용자 사진 목록
            if (user.userImages.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(user.userImages) { imageUrl ->
                        Box {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(242.dp)
                                    .clickable { onImageClick(imageUrl) }
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.Crop // ⭐️ 비율을 유지하며 공간을 채움
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd) // 먼저 우측 하단 정렬
                                    .padding(end = 16.dp, bottom = 16.dp) // 그 다음 여백
                                    .background(
                                        color = SisoColorTokens.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(99.dp)
                                    )
                                    .width(46.dp)
                                    .height(31.dp)
                            ) {
                                Text(
                                    text = "2/3",
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
                    model = com.likelion.ui.R.drawable.ic_location_on_24px,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "위치 : ${user.location}",
                    style = SisoTypoTokens.Label1,
                    color = SisoColorTokens.GrayScale90
                )
            }
            Spacer(Modifier.size(12.dp))
            // 닉네임 및 나이

            Text(
                text = "${user.nickname}, ${user.age}세",
                style = SisoTypoTokens.Title2, color = SisoColorTokens.GrayScale90
            )
            Spacer(Modifier.size(12.dp))
            // 음성 버튼

            AsyncImage(
                model = com.likelion.ui.R.drawable.ic_voicesample,
                contentDescription = "",
                modifier = Modifier
                    .width(130.dp)
                    .height(44.dp)
            )

            Spacer(Modifier.size(12.dp))
            // 관심사 목록
            Text(
                text = user.interests.joinToString(separator = " #", prefix = "#"),
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.GrayScale90
            )
            Spacer(Modifier.size(12.dp))
            // 자기소개
            Text(
                text = user.introduce,
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale90, modifier = Modifier.height(54.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.size(15.dp))

            Row(
                modifier = Modifier
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp) // ✅ 12dp 간격
            ) {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.ic_message_button,
                    contentDescription = "",
                  //  modifier = Modifier.weight(1f)
                    modifier = Modifier.size(80.dp)
                )
                AsyncImage(
                    model = com.likelion.ui.R.drawable.ic_call_button,
                    contentDescription = "",
                   // modifier = Modifier.weight(3f)\
                    modifier = Modifier.width(236.dp).height(80.dp)

                )


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
        userImages = listOf("url1", "url2", "url3"),
        location = "서울 강남구",
        nickname = "강남멋쟁이",
        age = 30,
        voiceUrl = "voice_url",
        interests = listOf("독서", "영화", "헬스"),
        introduce = "안녕하세요. 자기소개입니다. 저는 영화와 독서를 좋아합니다."
    )
    //UserCard(user = sampleUser)
}