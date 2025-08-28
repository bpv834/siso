package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

// 서버로부터 받을 통화 정보 데이터 클래스
data class CallInfoDto(
    @SerializedName("id") val id: Long, // 서버 DTO에 id 필드가 있으므로 추가
    @SerializedName("channelName") val channelName: String,
    @SerializedName("token") val token: String,
    @SerializedName("callerId") val callerId: Long, // 서버 DTO에 callerId 필드가 있으므로 추가
    @SerializedName("receiverId") val receiverId: Long // 서버 DTO에 receiverId 필드가 있으므로 추가
    // TODO: 서버 DTO에 uid 필드는 없으므로 제거했습니다. 필요하다면 서버와 협의 후 추가해야 합니다.
)