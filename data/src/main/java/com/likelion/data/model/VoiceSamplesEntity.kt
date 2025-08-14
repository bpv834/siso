package com.likelion.data.model


import com.google.gson.annotations.SerializedName

data class VoiceSamplesEntity(
    @SerializedName("id")
    val id: Long,  // BIGINT, PRIMARY KEY, Auto Increment

    @SerializedName("user_id")
    val userId: Long,  // BigInt, NOT NULL

    @SerializedName("url")
    val url: String?,  // VarChar(255), nullable

    @SerializedName("duration")
    val duration: Int?,  // Int, nullable

    @SerializedName("file_size")
    val fileSize: Int?,  // Int, nullable, 바이트 단위

    @SerializedName("created_at")
    val createdAt: String?,  // DateTime, nullable, ISO 8601 문자열

    @SerializedName("updated_at")
    val updatedAt: String?  // DateTime, nullable, ISO 8601 문자열
)