package com.likelion.remote.api

import com.likelion.remote.model.request.KakaoAuthRequest
import com.likelion.remote.model.response.TokenPairDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface KakaoAuthApiService {
    @POST("api/auth/kakao")
    suspend fun loginWithKakao(
        @Body body: KakaoAuthRequest
    ): TokenPairDto

    @POST("api/auth/refresh")
    suspend fun refreshAccessToken(
        @Header("Authorization") bearerRefreshToken: String
    ): TokenPairDto
}