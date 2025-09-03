package com.likelion.data.image.repository

import com.likelion.domain.image.model.ImageModel
import com.likelion.domain.image.repository.ImageRepository
import com.likelion.remote.api.ImageApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageRepository {
    override suspend fun getImagesByUserId(
        userId: String,
        accessToken: String
    ): Result<List<ImageModel>> {
        TODO("Not yet implemented")
    }

    /*    override suspend fun getImagesByUserId(
            userId: String,
            refreshToken: String
        ): Result<List<ImageModel>> {
            return try {
                val response: Response<List<ImageResponse>> =
                    imageApiService.getUserProfileImages(
                        userId = userId,
                        accessToken = "Bearer $refreshToken"
                    )

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
        }*/

    override suspend fun upLoadImage(
        imgPathList: List<String>,
        accessToken: String
    ): Result<Unit> {
        return try {
            // 1. 파일 리스트를 MultipartBody.Part 리스트로 변환
            val multipartParts = imgPathList.map { path ->
                val file = File(path)
                if (!file.exists()) {
                    throw IllegalArgumentException("File not found: $path")
                }
                // 파일 → RequestBody → MultipartBody.Part 변환
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.name, requestFile)
            }

            // 2. API 호출
            val response = imageApiService.uploadProfileImage(
                // 서버의 @RequestPart(value = "files")와 이름 일치
                files = multipartParts,
                accessToken = "Bearer $accessToken"
            )

            // 3. 응답 처리
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception("Image upload failed: ${response.code()} ${response.message()}")
                )
            }
        } catch (e: Exception) {
            // 업로드 중 발생하는 네트워크 오류 등 예외 처리
            Result.failure(e)
        }
    }
    override suspend fun getPresignedImgs(
        imgIds: List<Long>,
        accessToken: String
    ): List<String> = coroutineScope {
        val deferredList = imgIds.map { imgId ->
            async(Dispatchers.IO) {
                val response = imageApiService.getPresignedUserProfileImage(
                    imgId = imgId,
                    accessToken = "Bearer $accessToken"
                )
                if (response.isSuccessful) {
                    response.body() ?: throw Exception("Empty presigned URL for imgId: $imgId")
                } else {
                    throw Exception("Failed to get presigned URL for imgId: $imgId, code: ${response.code()}")
                }
            }
        }
        deferredList.awaitAll()
    }
}
