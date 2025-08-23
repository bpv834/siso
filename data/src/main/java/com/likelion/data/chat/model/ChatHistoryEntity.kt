package com.likelion.data.chat.model

data class ChatHistoryEntity(
    val profileImage: String,
    val currentMsg: String,
    val nickName: String,
    val callTime: Long,
    val isView: Boolean = false
)