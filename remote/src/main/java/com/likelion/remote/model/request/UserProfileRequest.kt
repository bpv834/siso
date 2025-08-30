package com.likelion.remote.model.request

import com.google.gson.annotations.SerializedName


// 서버의 UserProfileRequestDto와 매칭
data class UserProfileRequest(
    @SerializedName("age") val age: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("introduce") val introduce: String?,
    @SerializedName("sex") val sex: String?,
    @SerializedName("preferenceSex") val preferenceSex: String?,
    @SerializedName("drinkingCapacity") val drinkingCapacity: String?,
    @SerializedName("religion") val religion: String?,
    @SerializedName("smoke") val smoke: Boolean?,
    @SerializedName("location") val location: String?,
    @SerializedName("mbti") val mbti: String?,
    @SerializedName("meetings") val meetings : List<String> = listOf("CLUB_ACTIVITY","VOLUNTEER_ACTIVITY","HOBBY_GROUP")

)