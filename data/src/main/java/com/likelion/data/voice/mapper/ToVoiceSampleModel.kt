package com.likelion.data.voice.mapper

import com.likelion.domain.voice.model.VoiceSampleModel
import com.likelion.remote.model.response.VoiceSampleResponseDto

fun VoiceSampleResponseDto.toDomain(): VoiceSampleModel = VoiceSampleModel(
    id = this.id,
    url = this.url,
    duration = this.duration
)