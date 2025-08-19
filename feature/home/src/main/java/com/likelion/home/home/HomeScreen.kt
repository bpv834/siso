package com.likelion.home.home

import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import com.likelion.navigation.NavigationRoute


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
) {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Text(text = "HOME")
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}