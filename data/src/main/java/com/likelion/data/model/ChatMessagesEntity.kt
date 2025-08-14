package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class ChatMessagesEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("chatroom_id")
    val chatroomId: Long,  // BigInt, NOT NULL

    @SerializedName("sender_id")
    val senderId: Long,  // BigInt, NOT NULL

    @SerializedName("content")
    val content: String,  // TEXT, NOT NULL

    @SerializedName("created_at")
    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("updated_at")
    val updatedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("is_deleted")
    val isDeleted: Boolean  // Boolean, NOT NULL (소프트 딜리트)
)