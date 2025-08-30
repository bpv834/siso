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
    @SerializedName("location") val location: String = "Seoul",
    @SerializedName("sex") val sex: String = "MALE",
    @SerializedName("preferenceSex") val preferenceSex: String = "MALE",
    @SerializedName("profileImages") val profileImages: List<ImageResponse>,// ImageResponseDto 리스트
    @SerializedName("meetings") val meetings : List<String> = listOf("CLUB_ACTIVITY","VOLUNTEER_ACTIVITY","HOBBY_GROUP")
)
