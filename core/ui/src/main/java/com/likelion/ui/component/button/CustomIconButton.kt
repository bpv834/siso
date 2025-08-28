package com.likelion.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

// 예시 색상 토큰

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconPainter: Painter,
    contentDescription: String,
    backgroundColor: Color,
    iconColor: Color = Color.White // 아이콘 색상은 기본값을 White로 설정
) {
    Box(
        modifier = modifier
            .background(backgroundColor), // 외부에서 받은 배경색 적용
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier.fillMaxSize(), // Box의 크기를 꽉 채움
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = iconColor // 외부에서 받은 아이콘 색상 적용
            )
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = contentDescription, // 외부에서 받은 설명 적용
            )
        }
    }
}