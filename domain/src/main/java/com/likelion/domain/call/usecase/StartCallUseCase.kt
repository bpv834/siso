package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.CallModel
import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class StartCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(receiverId: Long, accessToken : String): Result<CallModel> {
        return callRepository.startCall(receiverId = receiverId, accessToken = accessToken)
    }
}