package com.likelion.domain.notification.usecase

import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.repository.FcmTokenRepository

class SendFcmTokenUseCase(
    private val repository: FcmTokenRepository
) {
    suspend operator fun invoke(token: FcmToken): Result<Unit> {
       return repository.sendToken(token)
    }
}