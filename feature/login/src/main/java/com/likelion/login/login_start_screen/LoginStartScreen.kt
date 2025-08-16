package com.likelion.login.login_start_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginStartScreen(
    viewModel: LoginStartScreenViewModel = LoginStartScreenViewModel()
){
    val scrollState = rememberScrollState()
    val introductionFontStyle = SisoTypoTokens.Body1
    val textBoxSize = 99
    Box(
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left
        ) {
            Spacer(Modifier.size(size = 61.dp))
            Column (
                Modifier.padding(start = 16.dp, end = 60.dp)
            ){
                Text(
                    modifier = Modifier.height( height = (textBoxSize/3).dp),
                    text = "시팅가입을 환영합니다",
                    style = introductionFontStyle,
                    color = SisoColorTokens.GrayScale90
                )
                Text(
                    modifier = Modifier.height( height = (textBoxSize/3).dp),
                    text = "내 정보를 입력하면",
                    style = introductionFontStyle,
                    color = SisoColorTokens.GrayScale90
                )
                Text(
                    modifier = Modifier.height( height = (textBoxSize/3).dp),
                    text = "좋은 인연을 만날 확률이 높아져요",
                    style = introductionFontStyle,
                    color = SisoColorTokens.GrayScale90
                )

            }
            Spacer(modifier = Modifier.size(size = 78.11.dp))
            AsyncImage(
                model = R.drawable.start_lock,
                contentDescription = ""
            )
            Spacer(Modifier.size(size = 59.1.dp))

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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
            Spacer(Modifier.size(size = 56.dp))
        }

    }


}

@Composable
@Preview
fun LoginStartScreenPreview(){
    Surface(color = SisoColorTokens.White) {
        LoginStartScreen()
    }

}