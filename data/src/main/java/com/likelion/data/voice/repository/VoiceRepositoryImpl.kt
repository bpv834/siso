package com.likelion.data.voice.repository

import com.likelion.data.voice.mapper.toDomain
import com.likelion.domain.voice.model.VoiceSampleModel
import com.likelion.domain.voice.repository.VoiceRepository
import com.likelion.remote.api.VoiceApiService
import okhttp3.MediaType.Companion.toMediaType
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
        refreshToken: String
    ): Result<VoiceSampleModel> {
        return try {
            val response = voiceApiService.getVoiceSamplesByUserId(
                refreshToken = "Bearer $refreshToken",
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
        refreshToken: String
    ) {
        val file = File(path)
        if (!file.exists()) {
            throw FileNotFoundException("File not found at $path")
        }

        // 파일 확장자 기반 MIME 타입 결정
        val mimeType = when (file.extension.lowercase()) {
            "m4a" -> "audio/mp4"
            "mp3" -> "audio/mpeg"
            "wav" -> "audio/wav"
            "ogg" -> "audio/ogg"
            else -> "application/octet-stream"
        }

        val requestFile = file.asRequestBody(mimeType.toMediaType())
        val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val response = voiceApiService.uploadVoiceSample(
            refreshToken = "Bearer $refreshToken",
            file = multipart
        )

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Upload failed: ${response.code()} : ${response.message()} | $errorBody")
        }

    }
}