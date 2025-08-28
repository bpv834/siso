package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

// 서버의 UserProfileResponseDto와 매칭
data class UserProfileResponseDto(
    @SerializedName("drinkingCapacity") val drinkingCapacity: String = "Occasionally",
    @SerializedName("religion") val religion: String = "None",
    @SerializedName("smoke") val smoke: Boolean = true,
    @SerializedName("age") val age: Int =31,
    @SerializedName("nickname") val nickname: String="test",
    @SerializedName("introduce") val introduce: String,
    @SerializedName("preferenceContact") val preferenceContact: String = "Call",
    @SerializedName("location") val location: String = "Seoul",
    @SerializedName("sex") val sex: String = "Male",
    @SerializedName("preferenceSex") val preferenceSex: String = "Male",
    @SerializedName("profileImages") val profileImages: List<ImageResponseDto> // ImageResponseDto 리스트
)
