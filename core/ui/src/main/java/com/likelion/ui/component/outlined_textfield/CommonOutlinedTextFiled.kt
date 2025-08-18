package com.likelion.ui.component.outlined_textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonOutlinedTextFiled(
    value: String, // 텍스트 필드의 현재 값
    onValueChange: (String) -> Unit, // 텍스트 변경 시 호출될 콜백
    modifier: Modifier = Modifier, // 외부에서 Modifier를 받을 수 있도록 추가
    placeholderText: String // 외부에서 placeholder 텍스트를 받을 수 있도록 추가
) {
    OutlinedTextField(
        value = value,
        colors = OutlinedTextFieldDefaults.colors(
            // SisoColorTokens에 있는 원하는 색상을 사용하거나, 직접 Color를 지정합니다.
            unfocusedContainerColor = SisoColorTokens.GrayScale20, // 포커스 없을 때 배경색
            focusedContainerColor = SisoColorTokens.GrayScale20,   // 포커스 있을 때 배경색

            // ✨ 테두리 색상을 투명하게 설정하여 없애기
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        onValueChange = onValueChange,
        placeholder = { // 💡 이제 placeholder를 외부에서 받은 텍스트로 사용
            Text(
                text = placeholderText,
                style = SisoTypoTokens.Label1, // placeholder 텍스트 스타일
                color = SisoColorTokens.GrayScale50
            )
        },
        modifier = modifier,
        shape = RoundedCornerShape(24.dp), // 💡 모서리를 둥글게 만듭니다.
        textStyle = SisoTypoTokens.Label1, // 입력될 텍스트 스타일
        // 입력될 텍스트 생상은?
        //  배경색 수정법 모르겠음...
    )
}
