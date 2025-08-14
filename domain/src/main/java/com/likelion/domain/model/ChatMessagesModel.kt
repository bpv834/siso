package com.likelion.domain.model


data class ChatMessagesModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val chatroomId: Long,  // BigInt, NOT NULL

    val senderId: Long,  // BigInt, NOT NULL

    val content: String,  // TEXT, NOT NULL

    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val updatedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val isDeleted: Boolean  // Boolean, NOT NULL (소프트 딜리트)
)