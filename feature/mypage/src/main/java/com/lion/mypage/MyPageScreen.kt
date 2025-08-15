package com.lion.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import androidx.compose.foundation.layout.R

@Composable
fun MyPageScreen()  {

    Column {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row {
            ProfileCircle(0.88F)
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            ) {
                Text(
                    text = "따뜻한 봄날",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.GrayScale90,
                )
                Text(
                    text = "56세",
                    style = SisoTypoTokens.Title3,
                    color = SisoColorTokens.GrayScale70,
                )
            }
        }

    }
}



@Composable
fun ProfileCircle(processFloat: Float) {
    var progress by remember { mutableFloatStateOf(processFloat) }
    Box {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(120.dp),
            color = SisoColorTokens.Gold40,
            strokeWidth = 4.dp,
            trackColor = SisoColorTokens.White,
            strokeCap = StrokeCap.Round,
        )
        AsyncImage(
            model = com.likelion.ui.R.drawable.example_profile,
            contentDescription = ""
        )
    }

}

@Preview
@Composable
fun GreetingPreview() {
    SisoTheme {
        MyPageScreen()
    }
}