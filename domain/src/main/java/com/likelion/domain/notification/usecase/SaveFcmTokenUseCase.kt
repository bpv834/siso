package com.likelion.domain.notification.usecase

import com.likelion.domain.login.repository.TokenRepository
import javax.inject.Inject

class SaveFcmTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(token: String) {
        print("invoke : $token")

        print("token : $token")
        repository.saveFcmToken(token)
    }
}