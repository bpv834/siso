package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun CustomOutlinedButton(
    modifier: Modifier,
    activeBorderColor: Color = SisoColorTokens.PrimaryColor,
    enableBorderColor: Color = SisoColorTokens.PrimaryColor,
    borderWidth: Dp = 1.dp,
    text: String,
    activeButtonColor: Color = SisoColorTokens.PrimaryColor,
    enableButtonColor: Color = SisoColorTokens.White,
    onclickButton: () -> Unit,
    isActive: Boolean,
    horizontalContentPadding : Dp = 16.dp
) {
    // isActive 상태에 따라 버튼의 색상과 테두리 색상을 결정
    val borderColors = if (isActive) activeBorderColor else enableBorderColor
    val backgroundColors = if (isActive) activeButtonColor else enableButtonColor

    OutlinedButton(
        onClick = onclickButton,
        modifier = modifier.clickable {
            if (isActive)
                onclickButton()
        },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColors
        ),
        contentPadding = PaddingValues(horizontal = horizontalContentPadding),
        border = BorderStroke(borderWidth, borderColors),
    ) {
        Text(
            text = text,
            color = SisoColorTokens.GrayScale90,
            style = SisoTypoTokens.Button1,
        )
    }
}