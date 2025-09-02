package com.likelion.domain.voice.usecase

import com.likelion.domain.voice.model.VoiceSampleModel
import com.likelion.domain.voice.repository.VoiceRepository
import javax.inject.Inject

class UploadVoiceSampleUseCase @Inject constructor(
    val repository: VoiceRepository
) {
    suspend fun execute(path: String, accessToken: String): Result<VoiceSampleModel> {
        print("UploadVoiceSampleUseCase execute")
        return repository.uploadVoiceSample(path = path, accessToken = accessToken)
    }
}