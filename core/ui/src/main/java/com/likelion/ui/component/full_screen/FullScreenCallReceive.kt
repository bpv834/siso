package com.likelion.ui.component.full_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens


@Composable
fun FullScreenCallReceive(
    onClickCall: () -> Unit,
    onClickBackButton: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.size(48.dp))
        AsyncImage(
            model = com.likelion.ui.R.drawable.ic_back,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(start = 16.dp)
                .clickable {
                    onClickBackButton()
                }
        )
        Spacer(Modifier.size(34.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "\uD83D\uDD12 안전한 대화를 위한 팁", style = SisoTypoTokens.Title2)
        }
        Spacer(Modifier.size(60.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.img_one,
                    contentDescription = "",
                    modifier = Modifier
                        .width(24.dp)
                        .height(27.dp)
                )
                Spacer(Modifier.size(20.dp))

                Text(
                    text = "통화 중 불편하거나 이상한 느낌이\n 든다면 바로 통화를 종료해주세요.", style = SisoTypoTokens.Body2
                )
            }
            Spacer(Modifier.size(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.img_two,
                    contentDescription = "",
                    modifier = Modifier
                        .width(24.dp)
                        .height(27.dp)
                )
                Spacer(Modifier.size(20.dp))

                Text(
                    text = "앱 내 신고 및 차단 기능을 적극 \n" +
                            "활용해주세요.", style = SisoTypoTokens.Body2
                )
            }
            Spacer(Modifier.size(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = com.likelion.ui.R.drawable.img_three,
                    contentDescription = "",
                    modifier = Modifier
                        .width(24.dp)
                        .height(27.dp)
                )
                Spacer(Modifier.size(20.dp))
                Text(
                    text = "다시 연결될 확률은 적어요. \n" +
                            "서로를 존중하며 대화해주세요.", style = SisoTypoTokens.Body2
                )
            }
            Spacer(Modifier.size(165.dp))
            CommonActiveButton(text = "전화 연결하기", onClick = {
                onClickCall()
            })
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFullScreenCallReceive() {
    SisoTheme {
        FullScreenCallReceive({}, {})
    }
}