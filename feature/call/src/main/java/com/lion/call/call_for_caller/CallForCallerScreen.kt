package com.lion.call.call_for_caller

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView


@Composable
fun CallerRouter(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
) {
   // HomeScreen(hiltViewModel<HomeScreenViewModel>())
    CallForCallerScreen()
}

@Composable
fun CallForCallerScreen (){
    Column {
        Text("전화발신화면")
    }
}