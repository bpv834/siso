package com.likelion.domain.notification.model

// foreground 알림 유저
data class UserModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String
)