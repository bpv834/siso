package com.likelion.ui.component.button

import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun CommonActiveButton(
    text: String,
    modifier: Modifier? = Modifier, // ✨ 외부에서 modifier를 받도록 설정
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = SisoColorTokens.Gold40,
            contentColor = SisoColorTokens.Gray90
        ),
        border = null,
        modifier = modifier ?: Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        Text(
            text = text,
            style = SisoTypoTokens.Button1,
            color = SisoColorTokens.Gray90,
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