package com.likelion.home.mypage

import kotlinx.coroutines.flow.StateFlow

data class MyPageUiState(
    val progressValue : Float = 0F,
    val userImages : String = "",
    val nickname : String = "",
    val age : String = "",
    val location : String = "",
)