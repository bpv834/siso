package com.likelion.remote.model.response

data class ChatRoomResponseDto(
    val id: Long,
    val otherUserNickname: String,
    val otherUserProfileImagePath: String,
    val memberCount: Int,
    val lastMessageContent: String,
    val lastMessageSentAt: String,
    val unreadMessageCount: Int
)