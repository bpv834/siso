package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

/**
 * 아이콘과 텍스트를 포함하고 커스텀 가능한 Outlined Button 컴포저블입니다.
 *
 * @param icon 버튼 안에 표시할 아이콘 컴포저블 (선택 사항).
 * @param text 버튼 안에 표시할 텍스트 (선택 사항).
 * @param onClick 버튼 클릭 시 실행될 람다 함수.
 * @param modifier 버튼에 적용할 추가적인 Modifier.
 */
@Composable
fun CommonOutlinedButtonWithIconVertical(
    icon: (@Composable () -> Unit)? = null,
    text: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp), // 둥근 모서리 24.dp 설정
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = SisoColorTokens.Gray5
        ),
        border = BorderStroke(width = 1.dp, color = SisoColorTokens.Gray30)
    ) {
        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            icon?.invoke() // 널체킹 후 아이콘 배치, 아이콘이 널이면 아무일도 안일어남
            if (icon != null && text != null) {
                // 아이콘과 텍스트가 모두 있을 경우 간격 추가
                Spacer(Modifier.size(4.dp))
            }
            Text(text=text?:"", style = SisoTypoTokens.Button1, color = SisoColorTokens.Gray90)
        }
    }
}

// CommonOutlinedButton 컴포넌트 미리보기
@Preview(showBackground = true)
@Composable
fun CommonOutlinedButtonPreview() {
    SisoTheme {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // 아이콘과 텍스트가 있는 버튼 예시
            CommonOutlinedButtonWithIconVertical(
                icon = { Icon(Icons.Filled.Add, contentDescription = "추가") },
                text = "항목 추가",
                onClick = { /* 클릭 이벤트 처리 */ },
                modifier = Modifier.padding(end = 8.dp)
            )

            // 텍스트만 있는 버튼 예시
            CommonOutlinedButtonWithIconVertical(
                text = "저장",
                onClick = { /* 클릭 이벤트 처리 */ },
                modifier = Modifier.padding(end = 8.dp)
            )

            // 아이콘만 있는 버튼 예시
            CommonOutlinedButtonWithIconVertical(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "좋아요") },
                onClick = { /* 클릭 이벤트 처리 */ }
            )
        }
    }
}
