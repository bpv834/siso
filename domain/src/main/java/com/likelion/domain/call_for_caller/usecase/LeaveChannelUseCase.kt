package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.repository.CallRepository
import javax.inject.Inject

class LeaveChannelUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend  fun execute(): Result<Unit> {
        return callRepository.leaveChannel()
    }
}
