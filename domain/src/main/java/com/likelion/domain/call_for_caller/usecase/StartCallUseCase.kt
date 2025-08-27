package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.model.CallInfoModel
import com.likelion.domain.call_for_caller.repository.CallRepository
import javax.inject.Inject

class StartCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(callerId: Long, receiverId: Long): Result<CallInfoModel> {
        return callRepository.startCall(callerId = callerId, receiverId = receiverId)
    }
}