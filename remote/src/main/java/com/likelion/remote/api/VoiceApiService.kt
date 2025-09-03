package com.likelion.remote.api

import com.likelion.remote.model.response.VoiceSampleResponseDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface VoiceApiService {

    // 1. 업로드
    @Multipart
    @POST("/api/voice-samples/upload")
    suspend fun uploadVoiceSample(
        @Header("Authorization") accessToken: String,
        @Part file: MultipartBody.Part
    ): Response<VoiceSampleResponseDto>

    // 2. 특정 사용자 음성 목록 조회
    @GET("/api/voice-samples/user/{userId}")
    suspend fun getVoiceSamplesByUserId(
        @Header("Authorization") accessToken: String,
        @Path("userId") userId: String
    ): Response<List<VoiceSampleResponseDto>>

    // 3. 음성 재생 (다운로드 방지)
    @GET("/api/voice-samples/play/{voiceId}")
    @Streaming // 스트리밍으로 받아야 함
    suspend fun playVoice(
        @Header("Authorization") accessToken: String,
        @Path("voiceId") voiceId: Long
    ): Response<ResponseBody>
}