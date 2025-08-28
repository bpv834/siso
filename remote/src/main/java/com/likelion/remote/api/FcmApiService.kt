package com.likelion.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FcmApiService {
    @POST("api/notifications")
    suspend fun sendToken(
        @Header("Authorization") jwt: String,
        @Body body: Map<String, String>
    )
}