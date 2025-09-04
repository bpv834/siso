package com.likelion.remote.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CallResponseDto(
    val accepted: Boolean,
    val token: String,
    val channelName: String,
    val callerId: Int,
    val receiverId: Int,
    val callStatus: String, // 혹은 enum 클래스로 정의 가능
    val duration: Int,
    val callerProfile: UserProfileResponse,
    val receiverProfile: UserProfileResponse
)