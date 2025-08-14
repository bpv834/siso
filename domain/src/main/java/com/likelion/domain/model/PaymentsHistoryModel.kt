package com.likelion.domain.model


data class PaymentsHistoryModel(
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    val userId: Long,  // BigInt, NOT NULL

    val paymentId: Long,  // BigInt, NOT NULL

    val item: String,  // Enum, NOT NULL

    val amount: Int,  // Int, NOT NULL

    val paymentStatus: String,  // Enum, NOT NULL (Pending, Success, Failed, Refunded)

    val paymentMethod: String,  // Enum, NOT NULL (Toss, Kakaopay)

    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)