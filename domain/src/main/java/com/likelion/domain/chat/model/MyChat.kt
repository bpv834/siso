package com.likelion.domain.chat.model

/**
 * 내가 보낸 메시지
 * @param msg 내가 보낸 메시지
 * @param time 시간 HH:mm*/
data class MyChat(
    val chatRoomId: Long,
    val msg: String,
    val time: String,
    val showTime: Boolean = false
)
