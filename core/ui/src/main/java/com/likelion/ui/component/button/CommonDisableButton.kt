package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens


@Composable
fun CommonDisableButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // ✨ 외부에서 modifier를 받도록 설정
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, SisoColorTokens.GrayScale40),
        colors = ButtonDefaults.buttonColors(
            containerColor = SisoColorTokens.GrayScale30,
            contentColor = SisoColorTokens.GrayScale50
        ),
        // ✨ 받은 modifier를 적용하고, padding을 추가합니다.
        modifier = modifier.fillMaxWidth() // <-- 이 부분이 버튼의 너비를 최대로 늘려줍니다.
    ) {
        Text(
            text = text,
            style = SisoTypoTokens.Button1,
            color = SisoColorTokens.GrayScale50,
            modifier= modifier.padding(vertical = (15.5).dp)
        )
    }
}

// 프리뷰
@Preview(showBackground = true)
@Composable
fun CommonDisableButtonPreview() {
    SisoTheme {
        CommonDisableButton(
            text = "계속하기",
            onClick = {}
        )
    }
}