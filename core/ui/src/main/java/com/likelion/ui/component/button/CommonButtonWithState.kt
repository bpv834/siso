package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun CommonButtonWithState(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean, // 버튼 활성화 상태

) {
    // isActive 상태에 따라 색상과 보더를 동적으로 결정
    val containerColor = if (isActive) SisoColorTokens.Gold40 else SisoColorTokens.GrayScale30
    val contentColor = if (isActive) SisoColorTokens.GrayScale90 else SisoColorTokens.GrayScale50

    OutlinedButton(
        onClick = onClick,
        enabled = isActive, // isActive 상태에 따라 버튼 활성화/비활성화
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = SisoColorTokens.GrayScale30, // 비활성화 상태 색상
            disabledContentColor = SisoColorTokens.GrayScale50 // 비활성화 상태 텍스트 색상
        ),
        modifier = Modifier.fillMaxWidth().height(54.dp),
        border = null,

    ) {
        Text(
            text = text,
            style = SisoTypoTokens.Button1,
            color = contentColor, // 여기서는 contentColor를 사용하여 텍스트 색상 결정
        )
    }
}