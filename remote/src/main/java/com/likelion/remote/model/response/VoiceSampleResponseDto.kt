package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

// 서버의 VoiceSampleResponseDto와 매칭
data class VoiceSampleResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("url") val url: String,
    @SerializedName("duration") val duration: Int, // 음성 길이 (초 단위, 최대 20초)
    @SerializedName("fileSize") val fileSize: Int, // 파일 크기 (바이트)
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)