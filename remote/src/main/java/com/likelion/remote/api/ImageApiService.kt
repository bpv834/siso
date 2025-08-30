package com.likelion.remote.api

import com.likelion.remote.model.response.ImageResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApiService {

    // 이미지 등록
    @Multipart
    @POST("/api/images/upload")
    suspend fun uploadProfileImage(
        @Header("Authorization") refreshToken: String,
        @Part image: MultipartBody.Part, // 실제 이미지 파일
        @Body userId: Long // 사용자 ID (String을 RequestBody로 변환)
    ): Response<ImageResponseDto> // 단일 이미지 업로드이므로 ImageResponse를 반환합니다.

    // 사용자별 이미지 조회
    @Multipart
    @POST("/api/images/{userId}")
    suspend fun getUserProfileImages(
        @Header("Authorization") refreshToken: String,
        @Body userId: Long
    ): Response<ImageResponseDto>

    // 이미지 삭제
    @Multipart
    @POST("/api/images/{imageId}")
    suspend fun deleteUserProfileImages(
        @Header("Authorization") refreshToken: String,
        @Body imageId: String
    ): Response<ImageResponseDto>

}