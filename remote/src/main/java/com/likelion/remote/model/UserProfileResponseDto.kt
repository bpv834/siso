package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

// 서버의 UserProfileResponseDto와 매칭
data class UserProfileResponseDto(
    @SerializedName("drinkingCapacity") val drinkingCapacity: String,
    @SerializedName("religion") val religion: String,
    @SerializedName("smoke") val smoke: Boolean,
    @SerializedName("age") val age: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("introduce") val introduce: String,
    @SerializedName("preferenceContact") val preferenceContact: String,
    @SerializedName("location") val location: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("preferenceSex") val preferenceSex: String,
    @SerializedName("profileImages") val profileImages: List<ImageResponseDto> // ImageResponseDto 리스트
)
