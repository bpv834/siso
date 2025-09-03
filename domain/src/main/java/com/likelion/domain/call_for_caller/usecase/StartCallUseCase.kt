package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.repository.CallRepository
import javax.inject.Inject

class StartCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(receiverId: Long, accessToken : String): Result<CallModel> {
        return callRepository.startCall(receiverId = receiverId, accessToken = accessToken)
    }
}