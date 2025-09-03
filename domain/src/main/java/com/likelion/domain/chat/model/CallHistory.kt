package com.likelion.domain.chat.model

data class CallHistory(
    val callId: Long,
    val nickName: String,
    val profileImage: String,
    val callTime: String,
    val isCallImageLoaded: Boolean = false,
    val isDelete: Boolean = false
)
