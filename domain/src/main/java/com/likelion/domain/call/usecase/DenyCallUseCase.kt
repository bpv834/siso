package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.CallModel
import com.likelion.domain.call.model.CallRejectResponseModel
import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject


class DenyCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(
        accessToken: String,
        request: CallModel
    ): Result<CallRejectResponseModel> {
        return callRepository.denyCall(accessToken = accessToken, request = request)
    }
}