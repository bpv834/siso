package com.likelion.remote.api

import com.likelion.remote.model.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ImageApiService {

    // 이미지 등록
    // 이미지 업로드 추가 (POST 요청)
    @Multipart
    @POST("/api/images/upload")
    suspend fun uploadProfileImage(
        @Header("Authorization") accessToken: String,
        @Part files: List<MultipartBody.Part>
    ): Response<List<ImageResponse>>

    // 사용자별 이미지 조회
    @GET("/api/images/user/{userId}")
    suspend fun getUserProfileImages(
        accessToken: String,
        @Path("userId") userId: String
    ): Response<List<ImageResponse>>

    // 이미지 삭제
    @Multipart
    @POST("/api/images/{imageId}")
    suspend fun deleteUserProfileImages(
        @Header("Authorization") refreshToken: String,
        @Body imageId: String
    ): Response<ImageResponse>

}