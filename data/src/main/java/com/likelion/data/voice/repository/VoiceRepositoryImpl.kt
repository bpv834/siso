package com.likelion.data.voice.repository

import com.likelion.data.voice.mapper.toDomain
import com.likelion.domain.voice.model.VoiceSampleModel
import com.likelion.domain.voice.repository.VoiceRepository
import com.likelion.remote.api.VoiceApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class VoiceRepositoryImpl @Inject constructor(
    private val voiceApiService: VoiceApiService
) : VoiceRepository {

    // 1. 특정 사용자 음성 샘플 조회
    override suspend fun getVoiceSample(
        userId: String,
        accessToken: String
    ): Result<VoiceSampleModel> {
        return try {
            val response = voiceApiService.getVoiceSamplesByUserId(
                accessToken = "Bearer $accessToken",
                userId = userId
            )

            if (response.isSuccessful) {
                // 서버가 최신순 정렬해주니까 첫 번째만 가져옴
                val dto = response.body()?.firstOrNull()
                    ?: return Result.failure(NoSuchElementException("No voice sample found"))
                Result.success(dto.toDomain())
            } else {
                Result.failure(Exception("Error ${response.code()} : ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 2. 음성 샘플 업로드
    override suspend fun uploadVoiceSample(
        path: String,
        accessToken: String
    ): Result<VoiceSampleModel> {
        return try {
            val file = File(path)
            if (!file.exists()) {
                return Result.failure(FileNotFoundException("File not found at $path"))
            }

            val mimeType = when (file.extension.lowercase()) {
                "m4a" -> "audio/mp4"
                "mp3" -> "audio/mpeg"
                "wav" -> "audio/wav"
                "ogg" -> "audio/ogg"
                else -> "application/octet-stream"
            }

            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
            val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val response = voiceApiService.uploadVoiceSample(
                accessToken = "Bearer $accessToken",
                file = multipart
            )

            if (response.isSuccessful) {
                // 서버 응답 본문을 VoiceSampleResponseDto로 변환
                val responseDto = response.body()
                if (responseDto != null) {
                    // ResponseDto를 Domain Model로 변환
                    val domainModel = responseDto.toDomain()
                    Result.success(domainModel)
                } else {
                    // 응답 본문이 비어있을 경우 실패 처리
                    Result.failure(Exception("Server returned an empty body"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Upload failed: ${response.code()} : ${response.message()} | $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}