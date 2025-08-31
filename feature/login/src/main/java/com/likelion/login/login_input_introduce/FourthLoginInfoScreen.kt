package com.likelion.login.login_input_introduce

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.button.CommonDisableButton
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.component.text_button.CommonTextButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens


@OptIn(ExperimentalMaterial3Api::class) // rememberNavController()와 관련된 경고를 없애기 위해 추가
@Composable
fun FourthLoginInfoScreen(
    viewModel: FourthLoginInfoScreenViewModelType = if (LocalInspectionMode.current) {
        // LocalInspectionMode -> Jetpack Compose Preview 환경에서만 true가 되는 값
        FakeFourthLoginInfoScreenViewModel()
    } else {
        hiltViewModel<FourthLoginInfoScreenViewModel>()
    },
    onNavigateNext: () -> Unit
) {
    // state 구독
    val bioText by viewModel.bioText.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.size(8.dp))
        AsyncImage(
            model = R.drawable.img_circle_bar_login4,
            contentDescription = ""
        )
        Spacer(Modifier.size(24.dp))
        Text(
            text = "간단한 자기소개를 작성해주세요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.Gray90
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "여러분의 진솔한 생각과 경험을 담아, 상대방이 \n" +
                    "당신을 더 잘 이해할 수 있도록 \n" +
                    "5자 이상, 50자 이하로 작성해 주세요.\n" +
                    "정보는 나중에 수정할 수 있어요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
        )
        Spacer(Modifier.size(24.dp))
        // 여기에 EditText(텍스트 필드) 추가
        CommonOutlinedTextFiled(
            modifier = Modifier
                .fillMaxWidth()
                .height(206.dp),
            placeholderText = "예시) 안녕하세요. 인생의 황혼기에 접어들었지만, 늘 새로운 경험과 사랑을 찾아 나아가고 있습니다.",
            value = bioText, // collect된 실시간 변경된 스트링 값을 넣는다.
            onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                viewModel.onBioTextChanged(newText)
            }
        )
        Spacer(Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), // Row가 전체 너비를 차지하도록 설정
            horizontalArrangement = Arrangement.End // 자식들을 Row의 끝(오른쪽)에 정렬
        ) {
            Text(
                text = "${bioText.length}/50",
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.Gray50
            )
        }
        Spacer(Modifier.size(112.dp))
        if (bioText.length in 5..50) CommonActiveButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp), text = "완료하기", onClick = {
                viewModel.saveBioTextInTemp()
                onNavigateNext()
            })
        else CommonDisableButton(text = "완료하기", onClick = {

        })
        Spacer(Modifier.size(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTextButton(
                text = "건너뛰기",
                style = SisoTypoTokens.Button2,
                color = SisoColorTokens.Gray50,
                onClick = { onNavigateNext() }
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun FourthLoginInfoScreenPreview() {
    SisoTheme {
        FourthLoginInfoScreen(onNavigateNext = {})
    }
}
