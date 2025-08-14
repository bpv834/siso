package com.likelion.domain.model


data class ChatRoomsModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val receiverId: Long,  // BigInt, NOT NULL

    val senderId: Long,  // BigInt, NOT NULL

    val callId: Long,  // BigInt, NOT NULL

    val lastMessageId: Long,  // PRIMARY, NOT NULL

    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val updatedAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)