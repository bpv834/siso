package com.likelion.login.state

import com.likelion.domain.login.model.User
import com.likelion.domain.login.model.UserStatus

// 자동로그인 시도
data class LoginUiState(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val userState: UserStatus = UserStatus.NONE,
    val user: User? = null,
    val error: String? = null,
)