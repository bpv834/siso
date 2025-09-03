package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

// 서버로부터 받을 통화 정보 데이터 클래스

@JsonClass(generateAdapter = true)
data class CallInfoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("channelName") val channelName: String,
    @SerializedName("token") val token: String,
    @SerializedName("callerId") val callerId: Long,
    @SerializedName("receiverId") val receiverId: Long
)