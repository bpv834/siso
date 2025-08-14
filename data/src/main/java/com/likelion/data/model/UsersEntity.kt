package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class UsersEntity(
    @SerializedName("id")
    val id: Long,  // BigInt, PRIMARY KEY, Auto Increment

    @SerializedName("provider")
    val provider: String,  // Enum, NOT NULL

    @SerializedName("phone_number")
    val phoneNumber: String,  // VarChar(20), NOT NULL

    @SerializedName("is_online")
    val isOnline: Boolean,  // Boolean, NOT NULL

    @SerializedName("notification_subscribed")
    val notificationSubscribed: Boolean,  // Boolean, NOT NULL

    @SerializedName("refreshtoken")
    val refreshToken: String,  // VarChar(255), NOT NULL

    @SerializedName("is_block")
    val isBlock: Boolean,  // Boolean, NOT NULL

    @SerializedName("is_deleted")
    val isDeleted: Boolean,  // Boolean, NOT NULL

    @SerializedName("created_at")
    val createdAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("updated_at")
    val updatedAt: String,  // DateTime, NOT NULL (ISO 8601 문자열)

    @SerializedName("deleted_at")
    val deletedAt: String?  // DateTime, NOT NULL? nullable로 처리 가능
)