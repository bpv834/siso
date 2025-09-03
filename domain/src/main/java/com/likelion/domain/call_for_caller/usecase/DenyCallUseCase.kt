package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.model.CallRejectResponseModel
import com.likelion.domain.call_for_caller.repository.CallRepository
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