package com.likelion.remote.api

import com.likelion.remote.model.response.KakaoAccessTokenRequestDto
import com.likelion.remote.model.response.BasicTokenResponseDto
import com.likelion.remote.model.response.UserInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface KakaoAuthApiService {
    @POST("api/auth/kakao")
    suspend fun postKakaoToken(
        @Body request: KakaoAccessTokenRequestDto
    ): Response<BasicTokenResponseDto>

    @POST("api/auth/refresh")
    suspend fun refreshServerToken(
        @Header("Authorization") refreshToken: String
    ): Response<UserInfoResponseDto>

    @DELETE("api/users/delete")
    suspend fun deleteUser(

    )

}