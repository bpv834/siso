package com.likelion.domain.call.usecase

import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class ToggleMuteUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    fun execute(isMuted: Boolean) {
        callRepository.toggleMute(isMuted)
    }
}
