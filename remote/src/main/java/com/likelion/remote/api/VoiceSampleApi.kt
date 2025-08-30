package com.likelion.remote.api

import com.likelion.remote.model.response.VoiceSampleResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VoiceSampleApi {

    // POST /api/voice-samples/upload
    // 음성 샘플 파일을 업로드합니다.
    @Multipart
    @POST("/api/voice-samples/upload")
    suspend fun uploadVoiceSample(
        @Header("Authorization") refreshToken: String,
        @Part voice: MultipartBody.Part, // 실제 음성 파일
        @Part("userId") userId: RequestBody // 사용자 ID
    ): Response<VoiceSampleResponseDto>

    // POST /api/voice-samples/upload
    // 음성 샘플 파일을 업로드합니다.
    @Multipart
    @POST("/api/voice-samples/user/{userId}")
    suspend fun getUserVoiceSample(
        @Header("Authorization") refreshToken: String,
        @Part voice: MultipartBody.Part, // 실제 음성 파일
        @Part("userId") userId: RequestBody // 사용자 ID
    ): Response<VoiceSampleResponseDto>

}