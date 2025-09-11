package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.CallModel
import com.likelion.domain.call.model.CallResponseModel
import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class EvaluationAfterCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(
        callModel: CallModel,
        isKeepGoing: Boolean,
        accessToken: String
    ): Result<CallResponseModel> {
        return callRepository.evaluationAfterEndCall(
            callModel = callModel,
            isKeepGoing = isKeepGoing,
            accessToken = accessToken
        )
    }
}