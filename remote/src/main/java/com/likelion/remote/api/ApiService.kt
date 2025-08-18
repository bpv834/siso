package com.likelion.remote.api

import com.likelion.remote.model.ExchangeRequest
import com.likelion.remote.model.ExchangeResponse
import com.likelion.remote.model.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    // 카카오 사용자 정보 API
    @GET("api/users/info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): UserInfoResponse

    @POST("api/auth/kakao/exchange")
    suspend fun exchangeKakaoToken(@Body body: ExchangeRequest): ExchangeResponse

    @GET("api/users/info")
    suspend fun getUserInfo(): UserInfoResponse
}