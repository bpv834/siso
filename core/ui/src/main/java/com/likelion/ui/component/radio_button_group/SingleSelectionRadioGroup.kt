package com.likelion.ui.component.radio_button_group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun ReportReasonRadioGroup(
    selectedReason: String?,
    onReasonSelected: (String) -> Unit
) {
    val options = listOf(
        "사칭 의심\n(본인 아님 / 프로필과 본인이 다름)",
        "부적절한 언행 (욕설·음란·폭력)",
        "스팸/광고",
        "괴롭힘/혐오 발언",
        "불법 콘텐츠 (마약·불법광고 등)",
        "개인정보 유출",
        "기타 (직접 작성)"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        options.forEach { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onReasonSelected(text) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedReason),
                    onClick = { onReasonSelected(text) }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    style = SisoTypoTokens.Label1
                )
            }
        }
    }
}

// Dummy data classes for preview purposes
data class UsersModel(val userImages: List<String>)


@Preview(showBackground = true)
@Composable
fun ReportReasonRadioGroupPreview() {
    // 1. 상태 변수를 생성합니다.
    var selectedReason by remember { mutableStateOf<String?>(null) }

    // 2. ReportReasonRadioGroup에 필요한 파라미터를 정확하게 전달합니다.
    ReportReasonRadioGroup(
        selectedReason = selectedReason,
        onReasonSelected = { newReason ->
            selectedReason = newReason
        }
    )
}