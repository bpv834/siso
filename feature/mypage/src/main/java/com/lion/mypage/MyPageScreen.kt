package com.lion.mypage

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun MyPageScreen()  {


    val profileOption = listOf(
        "통화 기록" to {},
        "차단 / 신고한 인연" to {},
        "매칭 필터 설정" to {},
    )

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.size(size =31.dp))
        Row {
            ProfileCircle(
                padding = 8.dp,
                processFloat = .36F
            )
            Column(
                modifier = Modifier.offset(x = ((-2).dp))
                    .padding(start = 14.dp, end = 10.dp)
            ) {
                Spacer(modifier = Modifier.size(size = 6.dp))
                Text(
                    text = "따뜻한 봄날",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.GrayScale90,
                )
                Spacer(modifier = Modifier.size(size = 2.dp))
                Text(
                    text = "56세",
                    style = SisoTypoTokens.Title3,
                    color = SisoColorTokens.GrayScale70,
                )
                Spacer(modifier = Modifier.size(size = 10.dp))
                Row {
                    AsyncImage(
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),
                        model = com.likelion.ui.R.drawable.rocation_icon,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(size = 2.dp))
                    Text(
                        text = "서울 중구",
                        style = SisoTypoTokens.Label1,
                        color = SisoColorTokens.GrayScale90,
                    )
                }

                Spacer(modifier = Modifier.size(size = 11.dp))
                AsyncImage(
                    modifier = Modifier
                        .size(width = 190.dp, height = 48.dp)
                        .offset(x = (-18).dp)
                        .clickable{
                            d("test","click")
                        },
                    model = com.likelion.ui.R.drawable.profile_edit,
                    contentDescription = ""
                )
/*                Box(
                    modifier = Modifier.size(194.dp, 48.dp)
                        .padding(end = 10.dp)
                ){
                    AsyncImage(
                        modifier = Modifier.align(Alignment.CenterStart)
                            .offset(x = (-10).dp)
                            .size(width = 15.dp, height = 16.dp),
                        model = com.likelion.ui.R.drawable.left_triangle,
                        contentDescription = ""
                    )
                    Box(
                        modifier = Modifier.padding(start = 5.dp)
                            .size(181.dp, 48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SisoColorTokens.Gold50),

                        ){

                        Row(
                            modifier = Modifier.padding(12.dp)
                                .align(Alignment.CenterStart)
                        ) {

                            Text(
                                modifier = Modifier.size(width = 125.dp, height = 23.dp),
                                text = "자기소개 완성하기",
                                style = SisoTypoTokens.SubTitle1,
                                color = SisoColorTokens.Black,
                            )
                            Spacer(modifier = Modifier.size(size = 8.dp))
                            AsyncImage(
                                modifier = Modifier.size(24.dp,24.dp),
                                model = com.likelion.ui.R.drawable.text_edit_gray70,
                                contentDescription = ""
                            )
                        }

                    }
                }*/
            }
        }
        Spacer(modifier = Modifier.size(size = 27.dp))

            Box (
                modifier = Modifier
                    .height(72.dp)
                    .background(SisoColorTokens.GrayScale20, RoundedCornerShape(24.dp))
            ){
                Text(
                    modifier = Modifier.align(Alignment.Center)
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                    text = "안녕하세요? 식물과 자연을 좋아하는 닉네임입니다.",
                    style = SisoTypoTokens.Body2,
                    color = SisoColorTokens.GrayScale90,
                )
            }


        Spacer(modifier = Modifier.size(size = 19.dp))

        Text(
            text = "계정정보",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale90,
        )

        Spacer(modifier = Modifier.size(size = 12.dp))

        profileOption.forEachIndexed { idx, (text,option)->
            ProfileText(input = text,option = option)
        }

    }
}



@Composable
fun ProfileCircle(
    padding: Dp,
    processFloat: Float
) {
    var progress by remember { mutableFloatStateOf(processFloat) }
    Box(
        modifier = Modifier.padding(start = padding)
            .size(128.dp, 153.dp)
    ) { // 박스 우선 순위 아래 부터 그려짐
        // 2 이미지가 다음으로 그러졈
        AsyncImage(
            modifier = Modifier.size(120.dp, 120.dp),
            model = com.likelion.ui.R.drawable.example_profile,
            contentDescription = ""
        )
        // 1 서클이 먼저 그려짐
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(128.dp)
                .offset(x = (-4).dp, y = (-4).dp),
            color = SisoColorTokens.Gold40,
            strokeWidth = 8.dp,
            trackColor = SisoColorTokens.White.copy(alpha = 0.0F),
            strokeCap = StrokeCap.Round,
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .size(96.dp, 43.dp)
                .border(2.dp, SisoColorTokens.Gold40, RoundedCornerShape(99.dp))
                .background(SisoColorTokens.GrayScale5, RoundedCornerShape(99.dp))
        ){
            Text(
                text = "36% 완성",
                style = SisoTypoTokens.SubTitle1,
                modifier = Modifier.align(Alignment.Center)
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            )
        }

    }

}

@Composable
fun ProfileText(
    input: String,
    option: () -> Unit
){
    Text(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .clickable{
                option()
            },
        text = input,
        style = SisoTypoTokens.Body2,
        color = SisoColorTokens.Black,
    )
    HorizontalDivider(
        thickness = 1.dp,
        color = SisoColorTokens.GrayScale30
    )

}

@Preview
@Composable
fun MyPageScreenPreview() {
    SisoTheme {
        Surface{
            MyPageScreen()
        }

    }
}