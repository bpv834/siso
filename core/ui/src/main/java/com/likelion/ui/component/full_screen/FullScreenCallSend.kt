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
fun FullScreenCallSend(
    onClickConfirm: () -> Unit,
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
            Text(text = " \uD83E\uDD1D 전화 시작 전 약속", style = SisoTypoTokens.Title2)
        }
        Spacer(Modifier.size(34.dp))
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
                    text = "상대방의 이름, 연락처, 주소 등 \n" +
                            "개인정보는 묻지 않아요.", style = SisoTypoTokens.Body2
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
                    text = "욕설, 정치·종교 논쟁 등\n" +
                            "무례한 질문은 금지예요.", style = SisoTypoTokens.Body2
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
                    text = "마음이 맞지 않더라도, \n" +
                            "예의를 지켜주세요.", style = SisoTypoTokens.Body2
                )
            }
            Spacer(Modifier.size(173.dp))
            CommonActiveButton(text = "확인했어요", onClick = {
                onClickConfirm()
            })
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFullScreenCallSend() {
    SisoTheme {
        FullScreenCallSend({}, {})
    }
}