package com.likelion.ui.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.call_for_caller.model.UsersModel
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.radio_button_group.ReportReasonRadioGroup
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetReport(
    badUser: UsersModel,
    onDismissRequest: () -> Unit,
    onClickReport: (String) -> Unit,
) {
    // State variables for the selected reason and custom text
    var selectedReason by remember { mutableStateOf<String?>(null) }
    var customReasonText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "닉네임님을\n아래 사유로 신고합니다.",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                style = SisoTypoTokens.SubTitle1
            )
            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            ) {
                Icon(
                    painter = painterResource(com.likelion.ui.R.drawable.ic_close_24px),
                    contentDescription = "Close"
                )
            }
        }
        Spacer(Modifier.size(16.dp))

        // User Profile Image
        AsyncImage(
            model = badUser.userImages[0],
            contentDescription = "User profile image",
            modifier = Modifier
                .size(120.dp)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(16.dp))

        // Radio button group
        ReportReasonRadioGroup(
            selectedReason = selectedReason,
            onReasonSelected = { newReason ->
                selectedReason = newReason
            }
        )

        // Conditional Text Field for "Other" option

        Spacer(Modifier.size(16.dp))
        if(selectedReason?.contains("기타") == true)
        OutlinedTextField(
            value = customReasonText,
            onValueChange = { customReasonText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(73.dp)
                .border(
                    width = 1.dp,
                    // You can change the color here if needed
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    shape = RoundedCornerShape(18.dp)
                ),
            shape = RoundedCornerShape(18.dp)
        )
        Spacer(Modifier.size(16.dp))
        // Report Button
        CommonActiveButton(
            onClick = {
                val finalReason = if (selectedReason == "기타 (직접 작성)") {
                    customReasonText.ifEmpty { "기타 (사유 미입력)" }
                } else {
                    selectedReason ?: "사유 미선택"
                }
                onClickReport(finalReason)
            },
            modifier = Modifier.fillMaxWidth(),
            text = "신고하기"
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomSheetReportPreview() {
    val fakeUser = UsersModel(
        id = 2L,

            "https://cdn.ntoday.co.kr/news/photo/202101/77115_50584_1928.jpg"
        ,
        location = "America",
        nickname = "여덟글자닉네임자",
        age = 65,
        interests = listOf(
            "풋볼",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
            "영222화",
        ),
    )

    // Using a Box with a background color to make the composable visible
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // A white background makes the UI clear
    ) {
        BottomSheetReport(
            badUser = fakeUser,
            onDismissRequest = {},
            onClickReport = {}
        )
    }
}