package com.likelion.domain.login.usecase

import com.likelion.domain.login.repository.AuthTokenRepository
import javax.inject.Inject

class SaveAuthTokenUseCase @Inject constructor(
    private val repository: AuthTokenRepository
) {
    suspend operator fun invoke(token: String) {
        repository.saveAuthToken(token = token)
    }
}