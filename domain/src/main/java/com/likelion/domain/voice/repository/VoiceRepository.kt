package com.likelion.domain.voice.repository

import com.likelion.domain.voice.model.VoiceSampleModel

interface VoiceRepository {
    suspend fun uploadVoiceSample(path: String, accessToken: String)
    suspend fun getVoiceSample(userId: String, refreshToken: String): Result<VoiceSampleModel>
}