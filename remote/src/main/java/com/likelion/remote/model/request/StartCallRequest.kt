package com.likelion.remote.model.request

import com.google.gson.annotations.SerializedName

// 통화 시작 요청 시 서버에 보낼 데이터 (필요하다면, 예를 들어 발신자/수신자 ID)
data class CallRequest(
    @SerializedName("receiverId") val receiverId: Long
)