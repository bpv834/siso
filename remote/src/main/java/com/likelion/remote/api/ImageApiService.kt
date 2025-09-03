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
import retrofit2.http.Query

interface ImageApiService {

    // 이미지 등록
    // 이미지 업로드 추가 (POST 요청)
    @Multipart
    @POST("/api/images/upload")
    suspend fun uploadProfileImage(
        @Header("Authorization") accessToken: String,
        @Part files: List<MultipartBody.Part>
    ): Response<List<ImageResponse>>


    // 이미지 삭제
    @Multipart
    @POST("/api/images/{imageId}")
    suspend fun deleteUserProfileImages(
        @Header("Authorization") refreshToken: String,
        @Body imageId: String
    ): Response<ImageResponse>

    // 이미지 변환
    @GET("/api/images/presigned-url")
    suspend fun getPresignedUserProfileImage(
        @Header("Authorization") accessToken: String,
        @Query("imagePath") imgId: Long
    ): Response<String>

}