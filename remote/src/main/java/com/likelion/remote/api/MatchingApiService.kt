package com.likelion.remote.api

import com.likelion.remote.model.response.MatchingUserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MatchingApiService {
    @GET("/api/filter/matching")
    suspend fun getMatchingUsers(
        @Header("Authorization") eccessToken: String,
    ): List<MatchingUserResponse>
}