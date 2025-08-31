package com.likelion.data.chat.model

data class MyChatEntity(
    val chatRoomId: Long,
    val msg: String,
    val time: Long,
    val showTime: Boolean
)
