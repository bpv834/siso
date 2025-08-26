package com.likelion.login.state

import com.likelion.domain.login.model.UserStatus

// 자동로그인 시도
data class LoginUiState(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val userState: UserStatus = UserStatus.NONE,
    val error: String? = null
)