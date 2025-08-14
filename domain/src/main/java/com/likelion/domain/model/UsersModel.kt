package com.likelion.domain.model


data class UsersModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val provider: String,  // Enum, NOT NULL

    val phoneNumber: String,  // VarChar(20), NOT NULL

    val isOnline: Boolean,  // Boolean, NOT NULL

    val notificationSubscribed: Boolean,  // Boolean, NOT NULL

    val refreshToken: String,  // VarChar(255), NOT NULL

    val isBlock: Boolean,  // Boolean, NOT NULL

    val isDeleted: Boolean,  // Boolean, NOT NULL

    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val updatedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    val deletedAt: String?  // DateTime, NOT NULL? nullable로 처리 가능
)