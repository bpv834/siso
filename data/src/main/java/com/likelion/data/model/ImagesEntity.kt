package com.likelion.data.model
import com.google.gson.annotations.SerializedName

data class ImagesEntity(
    @SerializedName("id")
    val id: Long,  // PRIMARY KEY, Auto Increment

    @SerializedName("user_id")
    val userId: Long,  // BigInt, NOT NULL

    @SerializedName("path")
    val path: String?,  // VarChar(255), nullable

    @SerializedName("server_image_name")
    val serverImageName: String,  // VarChar(255), NOT NULL

    @SerializedName("original_name")
    val originalName: String,  // VarChar(255), NOT NULL

    @SerializedName("created_at")
    val createdAt: String?,  // DateTime, nullable, ISO 8601 문자열

    @SerializedName("updated_at")
    val updatedAt: String  // DateTime, NOT NULL, ISO 8601 문자열
)