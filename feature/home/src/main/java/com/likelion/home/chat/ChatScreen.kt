package com.likelion.home.chat

import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

@Composable
fun ChatRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {}
) {
    ChatScreen()
}

@Composable
fun ChatScreen() {
    Text(text = "Chat")
}