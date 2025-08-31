package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

// ImageRequestDto는 파일 업로드 시에 직접적인 JSON Body로 사용되지 않고,
// Multipart 요청에서 userId를 별도의 Part로 보낼 때 사용될 수 있습니다.
// 여기서는 직접적인 DTO 정의보다는 API 인터페이스에서 MultipartBody.Part로 처리합니다.

// 서버의 ImageResponseDto와 매칭
data class ImageResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("path") val path: String, // 이미지 접근 URL
    @SerializedName("serverImageName") val serverImageName: String, // 서버 파일명
    @SerializedName("originalName") val originalName: String, // 원본 파일명
    @SerializedName("createdAt") val createdAt: String, // LocalDateTime은 String으로 받습니다.
    @SerializedName("updatedAt") val updatedAt: String // LocalDateTime은 String으로 받습니다.
)