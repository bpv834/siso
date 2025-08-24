package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun CustomOutlinedButtonWithCustomContentColor(
    // 아이콘과 텍스트의 색상을 결정하는 인자
    contentColor: Color,
    // 버튼의 배경색을 결정하는 인자
    containerColor: Color,
    icon: (@Composable () -> Unit)? = null,
    text: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor, // 인자로 받은 배경색 적용
            contentColor = contentColor // 인자로 받은 콘텐츠 색상 적용
        ),
        border = BorderStroke(width = 1.dp, color = SisoColorTokens.Gray30)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 아이콘과 텍스트에 인자로 받은 contentColor가 적용됩니다.
            icon?.invoke()
            if (icon != null && text != null) {
                Spacer(Modifier.size(4.dp))
            }
            Text(
                text = text ?: "",
                style = SisoTypoTokens.Button1,
                color = contentColor // 인자로 받은 콘텐츠 색상 적용
            )
        }
    }
}