package com.likelion.domain.voice.usecase

import com.likelion.domain.voice.model.VoiceSampleModel
import com.likelion.domain.voice.repository.VoiceRepository
import javax.inject.Inject

class GetVoiceSampleUseCase @Inject constructor(
    val repository: VoiceRepository
) {
    suspend fun execute(userId: String, refreshToken: String): Result<VoiceSampleModel> {
        return repository.getVoiceSample(userId = userId , refreshToken= refreshToken)
    }
}