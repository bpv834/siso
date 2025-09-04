package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.repository.CallRepository
import javax.inject.Inject

class ToggleMuteUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    fun execute(isMuted: Boolean) {
        callRepository.toggleMute(isMuted)
    }
}
