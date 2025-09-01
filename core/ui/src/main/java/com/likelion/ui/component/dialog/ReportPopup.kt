package com.likelion.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun ReportPopUpDialog(onClickClose : ()->Unit) {
    Dialog(
        onDismissRequest = {
            // 외부를 클릭했을 때 다이얼로그를 닫는 로직
        }
    ) {
        Card(
            modifier = Modifier
                .width(328.dp)
                .height(313.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(24.dp))
            ) {
                Spacer(Modifier.size(36.dp))
                Text(
                    "신고완료",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = SisoTypoTokens.Title2
                )
                Spacer(Modifier.size(36.dp))
                Text(
                    "접수되었습니다. \n" +
                            "신고 내용은 운영 정책에 따라 \n" +
                            "처리될 예정입니다.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = SisoTypoTokens.Body2,
                    color = SisoColorTokens.Gray60
                )

                Spacer(Modifier.size(36.dp))
                CommonActiveButton(
                    onClick = {
                        onClickClose()
                    },
                    text = "닫기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPopUpPreview() {
    SisoTheme {
        ReportPopUpDialog({})
    }
}