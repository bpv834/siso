package com.likelion.home.mypage

import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {}
) {
    MyPageScreen()
}

@Composable
fun MyPageScreen() {
    Text(text = "MyPage")
}