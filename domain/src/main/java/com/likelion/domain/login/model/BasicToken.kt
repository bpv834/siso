package com.likelion.domain.login.model

// 카카오액세스 토큰을 서버로 보냈을 때
// 서버에서 보내주는 값
data class BasicToken(
    val accessToken: String,
    val refreshToken: String,
    val userStatus: UserStatus,
    val hasProfile: Boolean
)