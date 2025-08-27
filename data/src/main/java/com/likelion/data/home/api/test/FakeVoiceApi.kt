package com.likelion.data.home.api.test

import com.likelion.data.model.VoiceSamplesEntity

// data/api/FakeVoiceApi.kt
class FakeVoiceApi {
    fun getVoiceSamplesEntity(): List<VoiceSamplesEntity> {
        return listOf(
            VoiceSamplesEntity(id = 1L, userId = 1L, url = "https://example.com/voice_sample1.mp3", duration = 30, fileSize = 100000, createdAt = null, updatedAt = null),
            VoiceSamplesEntity(id = 2L, userId = 2L, url = "https://example.com/voice_sample2.mp3", duration = 25, fileSize = 90000, createdAt = null, updatedAt = null)
        )
    }
}