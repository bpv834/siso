package com.likelion.login.login_start_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.login.first_loginInfo_screen.FontText
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginStartScreen(
    viewModel: LoginStartScreenViewModel = LoginStartScreenViewModel()
){
    val sideDp = 16.dp
    val scrollState = rememberScrollState()
    val introductionFontStyle = SisoTypoTokens.Body1
    Column {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left
        ) {
            Spacer(Modifier.padding(50.dp))
            Column (
                Modifier.padding(start = sideDp, end = sideDp)
            ){
                FontText(text = "시팅가입을 환영합니다", style = introductionFontStyle, padding = 0.dp,)
                FontText(text = "내 정보를 입력하면", style = introductionFontStyle, padding = 0.dp,)
                FontText(
                    text = "좋은 인연을 만날 확률이 높아져요",
                    style = introductionFontStyle,
                    padding = 0.dp,
                )

            }
            Spacer(Modifier.padding(35.dp))
            AsyncImage(
                model = R.drawable.start_lock,
                contentDescription = ""
            )
            Spacer(Modifier.padding(30.dp))

        }
        Button(
            modifier = Modifier.padding(start = sideDp, end = sideDp)
                .fillMaxWidth()
                .height(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Orange30,
                disabledContainerColor = SisoColorTokens.GrayScale50
            ),
            onClick = {
                viewModel.fistContinueBooleanUpdate(true)
            },
            enabled = viewModel.fistContinueBoolean
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,
                color = if(viewModel.fistContinueBoolean == true)SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale50,
                fontSize = 22.sp
            )
        }
        Spacer(Modifier.padding(12.dp))
    }


}

@Composable
@Preview
fun LoginStartScreenPreview(){
    Surface(color = SisoColorTokens.White) {
        LoginStartScreen()
    }

}