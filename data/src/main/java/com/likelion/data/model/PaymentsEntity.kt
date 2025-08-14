package com.likelion.data.model


import com.google.gson.annotations.SerializedName

data class PaymentsEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("user_id")
    val userId: Long,  // BigInt, NOT NULL

    @SerializedName("method")
    val method: String,  // VarChar(50), NOT NULL

    @SerializedName("status")
    val status: String,  // Enum, NOT NULL

    @SerializedName("created_at")
    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)
