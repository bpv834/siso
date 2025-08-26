package com.likelion.remote.model.request

import com.google.gson.annotations.SerializedName

// 서버의 UserProfileRequestDto와 매칭
data class UserProfileRequest(
    @SerializedName("drinkingCapacity") val drinkingCapacity: String, // Enum (e.g., "NEVER", "OCCASIONALLY", "FREQUENTLY")
    @SerializedName("religion") val religion: String, // Enum (e.g., "CHRISTIAN", "BUDDHIST", "NONE")
    @SerializedName("smoke") val smoke: Boolean,
    @SerializedName("age") val age: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("introduce") val introduce: String,
    @SerializedName("preferenceContact") val preferenceContact: String, // Enum (e.g., "CALL", "MESSAGE")
    @SerializedName("location") val location: String, // Enum (e.g., "SEOUL", "BUSAN")
    @SerializedName("sex") val sex: String, // Enum (e.g., "MALE", "FEMALE")
    @SerializedName("preferenceSex") val preferenceSex: String // Enum (e.g., "ANY", "MALE", "FEMALE")
)