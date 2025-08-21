package com.likelion.domain.home.usecase

import com.likelion.domain.home.model.CallInfoModel
import com.likelion.domain.home.repository.CallRepository
import javax.inject.Inject

class StartCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(callerId: Long, receiverId: Long): Result<CallInfoModel> {
        return callRepository.startCall(callerId = callerId, receiverId = receiverId)
    }
}