package com.likelion.remote.api

import com.likelion.remote.model.response.UserProfileResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    // 새 API: userId로 유저 프로필 가져오기
    @GET("api/users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Long
    ): UserProfileResponseDto

}