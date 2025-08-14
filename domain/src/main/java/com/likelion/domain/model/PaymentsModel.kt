package com.likelion.domain.model



data class PaymentsModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val userId: Long,  // BigInt, NOT NULL

    val method: String,  // VarChar(50), NOT NULL

    val status: String,  // Enum, NOT NULL

    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)
