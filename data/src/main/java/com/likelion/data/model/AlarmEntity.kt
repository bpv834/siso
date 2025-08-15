package com.likelion.data.model
import com.google.gson.annotations.SerializedName


data class AlarmEntity(
    @SerializedName("id")
    val id: Long,  // BIGINT, PRIMARY KEY

    @SerializedName("receiver_id")
    val receiverId: Long,  // BIGINT, NOT NULL

    @SerializedName("sender_id")
    val senderId: Long,  // BIGINT, NOT NULL

    @SerializedName("sender_nickname")
    val senderNickname: String,  // VarChar(50), NOT NULL

    @SerializedName("title")
    val title: String,  // VarChar(50), NOT NULL

    @SerializedName("type")
    val type: String?,  // Enum, nullable

    @SerializedName("message")
    val message: String,  // Text, NOT NULL

    @SerializedName("is_read")
    val isRead: Boolean,  // Boolean, NOT NULL

    @SerializedName("created_at")
    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("url")
    val url: String  // VarChar(255), NOT NULL
)