package com.likelion.domain.call.usecase

import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class LeaveChannelUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend  fun execute(): Result<Unit> {
        return callRepository.leaveChannel()
    }
}
