package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class ChatRoomsEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("receiver_id")
    val receiverId: Long,  // BigInt, NOT NULL

    @SerializedName("sender_id")
    val senderId: Long,  // BigInt, NOT NULL

    @SerializedName("call_id")
    val callId: Long,  // BigInt, NOT NULL

    @SerializedName("last_message_id")
    val lastMessageId: Long,  // PRIMARY, NOT NULL

    @SerializedName("created_at")
    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("updated_at")
    val updatedAt: String  // DateTime, NOT NULL (ISO 8601 문자열)
)