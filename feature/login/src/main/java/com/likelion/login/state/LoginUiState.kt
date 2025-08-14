package com.likelion.login.state

data class LoginUiState(
    val isLoggedIn: Boolean = false,
    val kakaoToken: String? = null
)