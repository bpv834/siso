package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun CommonActiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // ✨ 외부에서 modifier를 받도록 설정
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = SisoColorTokens.Gold40,
            contentColor = SisoColorTokens.GrayScale90
        ),
        border = null,
        modifier = modifier.fillMaxWidth().height(54.dp)
    ) {
        Text(
            text = text,
            style = SisoTypoTokens.Button1,
            color = SisoColorTokens.GrayScale90,
        )
    }
}
// 프리뷰
@Preview(showBackground = true)
@Composable
fun CommonActiveButtonPreview() {
    SisoTheme {
        CommonActiveButton(
            text = "계속하기",
            onClick = {}
        )
    }
}