package com.likelion.remote.model.request

import com.google.gson.annotations.SerializedName

// FCM 토큰 등록/해제 요청 시 서버에 보낼 데이터
data class FcmTokenRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("token") val token: String
)