package com.likelion.login

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
) {
    LoginScreen()
}

@Composable
fun LoginScreen() {
    Scaffold { innerPadding ->
        AsyncImage(
            model = R.drawable.bg,
            contentDescription = "바탕화면"
        )

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 54.dp, top = 120.dp),
                    text = "지금도,\n사랑하기 딱 좋은 나이",
                    style = SisoTypoTokens.H1,
                    color = SisoColorTokens.Orange100
                )
                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 207.dp, top = 8.dp),
                    text = "시팅",
                    style = SisoTypoTokens.H1,
                    fontSize = 72.sp,
                    color = SisoColorTokens.Orange100
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 145.dp, top = 601.dp)
                    .align(Alignment.BottomCenter)
            ) {
                // 추후 카카오 이미지로 변경
                Text("카카오로 로그인")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SisoTheme {
        LoginScreen()
    }
}