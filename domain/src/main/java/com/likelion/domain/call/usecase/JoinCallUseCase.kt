package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.CallModel
import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class JoinCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    suspend fun execute(agoraToken: String, channelName: String) {
        return callRepository.joinCall(agoraToken = agoraToken, channelName = channelName)
    }
}