package com.likelion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun SisoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = SisoColorTokens.PrimaryColor,
            background = SisoColorTokens.Gray5,
            surface = SisoColorTokens.Gray5
        ),
    ) {
        content()
    }
}