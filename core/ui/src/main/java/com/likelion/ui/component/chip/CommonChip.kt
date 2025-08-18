package com.likelion.ui.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens


@Composable
fun CommonChip(
    text: String,
    isSelected: Boolean, // 토글여부
    onClick: (String) -> Unit, // 콜백
) {
    // 선택 상태에 따라 배경색, 텍스트색, 테두리색을 동적으로 결정
    val backgroundColor =
        if (isSelected) SisoColorTokens.Gold40 else SisoColorTokens.GrayScale20
    val textColor = SisoColorTokens.GrayScale90


    Surface(
        modifier = Modifier
            .clickable(onClick = {onClick(text)}), // 클릭 가능하게 만듦
        shape = RoundedCornerShape(999.dp), // 둥근 모서리 모양
        border = null, // 동적으로 변경되는 테두리 색상 적용
        color = backgroundColor // 동적으로 변경되는 배경색 적용
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            color = textColor, // 동적으로 변경되는 텍스트 색상 적용
            style = SisoTypoTokens.SubTitle1
        )
    }
}

@Preview
@Composable
fun CommonChipPreview(
) {
    CommonChip("음악감상",false, {})
}
