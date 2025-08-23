package com.likelion.domain.chat.model

data class ChatHistory(
    val id: Long=0,
    val nickName: String,
    val profileImage: String,
    val callTime: String,
    val currentMsg: String,
    val isNew: Boolean = false,
    val isDelete: Boolean = false
)
