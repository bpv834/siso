package com.likelion.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Test() {
    Button(onClick = {}) {
        Text(text = "test")
    }
}

@Composable
fun TestButton() {
    Button(onClick = {}) {
        Text(text = "testButton")
    }
}

@Composable
@Preview
fun TestPreview() {
    Test()
}

@Composable
@Preview
fun TestButtonPreview() {
    TestButton()
}