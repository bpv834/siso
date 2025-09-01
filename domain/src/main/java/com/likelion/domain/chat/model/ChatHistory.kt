package com.likelion.domain.chat.model

data class ChatHistory(
    val chatRoomId: Long,
    val nickName: String,
    val profileImage: String,
    val callTime: String,
    val currentMsg: String,
    val isNew: Boolean = false,
    val isChatImageLoaded: Boolean = false,
    val isDelete: Boolean = false
)
