package com.likelion.data.image.repository

import com.likelion.data.image.mapper.toDomain
import com.likelion.domain.image.model.ImageModel
import com.likelion.domain.image.repository.ImageRepository
import com.likelion.remote.api.ImageApiService
import com.likelion.remote.model.response.ImageResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageRepository {

    override suspend fun getImagesByUserId(
        userId: String,
        refreshToken: String
    ): Result<List<ImageModel>> {
        return try {
            val response: Response<List<ImageResponse>> =
                imageApiService.getUserProfileImages(userId = userId, refreshToken = refreshToken)

            if (response.isSuccessful) {
                val imageResponseList = response.body() ?: emptyList()
                val domainList = imageResponseList.map { it.toDomain() }
                Result.success(domainList)
            } else {
                Result.failure(Exception("API Error: HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun upLoadImage(
        imgPathList: List<String>,
        refreshToken: String
    ): Result<Unit> {
        return try {
            for (path in imgPathList) {
                val file = File(path)
                if (!file.exists()) {
                    return Result.failure(IllegalArgumentException("File not found: $path"))
                }

                // 파일 → MultipartBody.Part
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                // API 호출
                // TODO: imageApiService의 업로드 메서드 시그니처와 일치시키세요.
                // 이 예시에서는 refreshToken을 헤더로 넘기는 것을 가정합니다.
                val response = imageApiService.uploadProfileImage(
                    files = imagePart, refreshToken = refreshToken
                )

                if (!response.isSuccessful) {
                    return Result.failure(
                        Exception("Image upload failed: ${response.code()} ${response.message()}")
                    )
                }
            }

            // 모든 업로드가 성공했을 때 Unit 반환
            Result.success(Unit)
        } catch (e: Exception) {
            // 업로드 중 발생하는 네트워크 오류, 타임아웃 등 예외 처리
            Result.failure(e)
        }
    }
}