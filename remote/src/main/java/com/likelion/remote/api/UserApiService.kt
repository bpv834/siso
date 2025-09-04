package com.likelion.remote.api

import com.likelion.remote.model.request.UserProfileRequest
import com.likelion.remote.model.response.UserInfo
import com.likelion.remote.model.response.UserProfileResponse
import com.likelion.remote.model.response.UserInfoResponse
import com.likelion.remote.model.response.UserProfileResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @GET("api/auth/info")
    suspend fun  getUserId(
        @Header("Authorization") refreshToken: String,
    ): Response<UserInfo>


    // 새 API: userId로 유저 프로필 가져오기
    @GET("api/users/{id}")
    suspend fun getUserProfile(
        @Header("Authorization") refreshToken: String,
        @Path("id") userId: Long,
    ): Response<UserProfileResponse>


    // POST /api/profiles
    // 사용자 프로필 정보를 등록합니다.
    @POST("api/profiles")
    suspend fun registerUserProfile(
        @Header("Authorization") refreshToken: String,
        @Body request: UserProfileRequest
    ): Response<UserProfileRequest>

    // POST /api/profiles
    // 사용자 프로필 정보를 등록합니다.
    @POST("api/users/notification")
    suspend fun updateNotification(
        @Header("Authorization") accessToken: String, // AccessToken을 사용하도록 변경하는 것이 더 일반적입니다.
        @Body request: NotificationRequest
    ): Response<Unit> // 반환 값이 없으므로 Unit을 사용합니다.



}