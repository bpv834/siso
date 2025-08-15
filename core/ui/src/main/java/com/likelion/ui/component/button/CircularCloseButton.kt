package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.likelion.ui.theme.SisoColorTokens

@Composable
fun CircularCloseButton(
    bitmap: android.graphics.Bitmap,
    onClick: (bitmap: Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    // Box를 사용하여 원형 배경과 아이콘을 겹쳐서 배치
    Box(
        // modifier 체인은 순서가 중요합니다.
        modifier = modifier
            // Box의 크기를 반지름 14dp에 맞게 지름 28dp로 설정
            .size(28.dp)
            // 원형으로 클리핑 (자르기)
            .clip(CircleShape)
            // 배경색을 #F0F0F0으로 지정
            .background(SisoColorTokens.GrayScale10)
            // 클릭 가능하도록 설정

            .border(
                BorderStroke(3.dp, Color.White),
                CircleShape
            ),
        // Box의 콘텐츠를 중앙에 정렬
        contentAlignment = Alignment.Center
    ) {
        // 아이콘을 Box 안에 배치
        Icon(
            // painterResource를 사용해 드로어블 리소스를 가져옵니다.
            painter = painterResource(id = com.likelion.ui.R.drawable.ic_close_24px),
            contentDescription = "닫기 아이콘",
            // 아이콘 색상을 설정합니다.
            tint = Color.Gray,
            // 아이콘 크기 설정 (전체 버튼 크기보다 작게)
            modifier = Modifier
                .size(16.dp)
                .clickable { onClick(bitmap) }
        )
    }
}