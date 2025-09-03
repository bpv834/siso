package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.model.CallResponseModel
import com.likelion.domain.call_for_caller.repository.CallRepository
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