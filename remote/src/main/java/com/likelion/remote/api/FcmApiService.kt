package com.likelion.remote.api

import com.likelion.remote.model.request.FcmTokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FcmApiService {
    @POST("/api/fcm/token")
    suspend fun sendToken(
        @Header("Authorization") jwt: String,
        @Body body: FcmTokenRequest
    )
}