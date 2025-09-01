package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class MatchesEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("receiver_id")
    val receiverId: Long,  // BigInt, NOT NULL

    @SerializedName("sender_id")
    val senderId: Long,  // BigInt, NOT NULL

    @SerializedName("like")
    val like: Boolean,  // Boolean, NOT NULL

    @SerializedName("status")
    val status: String,  // Enum, NOT NULL

    @SerializedName("create_at")
    val createdAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)