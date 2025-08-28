package com.likelion.domain.chat.model

data class CallHistory(
    val id: Long = 0,
    val nickName: String,
    val profileImage: String,
    val callTime: String,
    val isCallImageLoaded: Boolean = false,
    val isDelete: Boolean = false
)
