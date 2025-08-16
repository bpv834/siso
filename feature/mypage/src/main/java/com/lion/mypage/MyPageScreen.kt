package com.lion.mypage

import android.R.attr.contentDescription
import android.R.attr.end
import android.R.attr.text
import android.R.attr.top
import android.util.Log.d
import android.widget.Button
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import coil3.compose.AsyncImage
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun MyPageScreen()  {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.size(size =31.dp))
        Row {
            ProfileCircle(
                padding = 8.dp,
                processFloat = .88F
            )
            Column(
                modifier = Modifier.offset(x = ((-2).dp))
                    .padding(end = 10.dp)
            ) {
                Spacer(modifier = Modifier.size(size = 22.dp))
                Text(
                    modifier = Modifier.padding(start = 18.dp),
                    text = "따뜻한 봄날",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.GrayScale90,
                )
                Text(
                    modifier = Modifier.padding(start = 18.dp),
                    text = "56세",
                    style = SisoTypoTokens.Title3,
                    color = SisoColorTokens.GrayScale70,
                )
                Spacer(modifier = Modifier.size(size = 16.dp))
                AsyncImage(
                    modifier = Modifier
                        .size(width = 194.dp, height = 48.dp)
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
        Spacer(modifier = Modifier.size(size = 37.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth().height(100.dp)
                    .border(width = 1.dp, color = SisoColorTokens.GrayScale30)
            ) {

            }
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
    ) { // 박스 우선 순위 아래 부터 그려짐
        // 2 이미지가 다음으로 그러졈
        AsyncImage(
            modifier = Modifier.offset(x = 3.dp,2.dp)
                .size(120.dp),
            model = com.likelion.ui.R.drawable.example_profile,
            contentDescription = ""
        )
        // 1 서클이 먼저 그려짐
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(124.dp),
            color = SisoColorTokens.Gold40,
            strokeWidth = 4.dp,
            trackColor = SisoColorTokens.White.copy(alpha = 0.0F),
            strokeCap = StrokeCap.Round,
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .offset(y = (27.1).dp)
                .size(92.dp, 39.dp)
                .border(2.dp, SisoColorTokens.Gold40, RoundedCornerShape(99.dp))
                .background(SisoColorTokens.White, RoundedCornerShape(99.dp))
        ){
            Text(text = "36% 완성",
                style = SisoTypoTokens.SubTitle1,
                modifier = Modifier.align(Alignment.Center),
            )
        }




    }

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