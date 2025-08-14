package com.likelion.domain.model


data class MatchesModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val receiverId: Long,  // BigInt, NOT NULL

    val senderId: Long,  // BigInt, NOT NULL

    val like: Boolean,  // Boolean, NOT NULL

    val status: String,  // Enum, NOT NULL

    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)