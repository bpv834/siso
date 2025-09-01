package com.likelion.data.chat.model

data class CallHistoryEntity(
    val callId: Long,
    val profileImage: String,
    val nickName: String,
    val callTime: Long
)