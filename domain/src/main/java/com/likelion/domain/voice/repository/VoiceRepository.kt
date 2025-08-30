package com.likelion.domain.voice.repository

import com.likelion.domain.voice.model.VoiceSampleModel
import javax.inject.Inject

interface VoiceRepository {
    suspend fun uploadVoiceSample(path: String, refreshToken: String)
    suspend fun getVoiceSample(userId: String, refreshToken: String): Result<VoiceSampleModel>
}