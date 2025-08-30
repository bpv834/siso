package com.likelion.domain.voice.usecase

import com.likelion.domain.voice.repository.VoiceRepository
import javax.inject.Inject

class UploadVoiceSampleUseCase @Inject constructor(
    val repository: VoiceRepository
) {
    suspend fun execute(path: String, refreshToken: String): Result<Unit> {
        return repository.uploadVoiceSample(path = path, refreshToken = refreshToken)
    }
}