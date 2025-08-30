package com.likelion.remote.api

import com.likelion.remote.model.request.UserProfileRequest
import com.likelion.remote.model.response.UserProfileResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    // 새 API: userId로 유저 프로필 가져오기
    @GET("api/users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Long
    ): UserProfileResponseDto


    // POST /api/profiles
    // 사용자 프로필 정보를 등록합니다.
    @POST("api/profiles")
    suspend fun registerUserProfile(
        @Header("Authorization") refreshToken: String,
        @Body request: UserProfileRequest
    ): Response<UserProfileResponseDto>


}