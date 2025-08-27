package com.likelion.domain.login.model

// 사용자정보
data class User(
    val accessToken: String,
    val refreshToken: String,
    val userStatus: UserStatus,
    val userInfo: UserInfo
)

data class UserInfo(
    val id: Long,
    val provider: String,
    val email: String,
    val phoneNumber: String,
    val deleted: Boolean,
    val block: Boolean
)

enum class UserStatus {
    LOGIN,
    REGISTER,
    NONE
}

