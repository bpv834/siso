package com.likelion.remote.api

import com.likelion.remote.model.response.UserInterestResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface InterestApiService {

    @GET("/api/interests/list")
    suspend fun getInterests(
        @Header("Authorization") refreshToken: String,
    ): Response<UserInterestResponseDto>

}