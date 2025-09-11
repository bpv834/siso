package com.likelion.domain.call.usecase

import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class ToggleSpeakerUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    fun execute(isSpeakerOn: Boolean) {
        callRepository.toggleSpeaker(isSpeakerOn)
    }
}