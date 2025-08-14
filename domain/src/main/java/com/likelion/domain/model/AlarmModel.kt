package com.likelion.domain.model


data class AlarmModel(
    val id: Long,  // BIGINT, PRIMARY KEY

    val receiverId: Long,  // BIGINT, NOT NULL

    val senderId: Long,  // BIGINT, NOT NULL

    val senderNickname: String,  // VarChar(50), NOT NULL

    val title: String,  // VarChar(50), NOT NULL

    val type: String?,  // Enum, nullable

    val message: String,  // Text, NOT NULL

    val isRead: Boolean,  // Boolean, NOT NULL

    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val url: String  // VarChar(255), NOT NULL
)