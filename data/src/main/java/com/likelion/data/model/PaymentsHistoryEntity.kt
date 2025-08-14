package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class PaymentsHistoryEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("user_id")
    val userId: Long,  // BigInt, NOT NULL

    @SerializedName("payment_id")
    val paymentId: Long,  // BigInt, NOT NULL

    @SerializedName("item")
    val item: String,  // Enum, NOT NULL

    @SerializedName("amount")
    val amount: Int,  // Int, NOT NULL

    @SerializedName("payment_status")
    val paymentStatus: String,  // Enum, NOT NULL (Pending, Success, Failed, Refunded)

    @SerializedName("payment_method")
    val paymentMethod: String,  // Enum, NOT NULL (Toss, Kakaopay)

    @SerializedName("created_at")
    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)