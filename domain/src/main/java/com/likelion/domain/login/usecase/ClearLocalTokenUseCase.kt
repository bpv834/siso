package com.likelion.domain.login.usecase

import com.likelion.domain.login.repository.TokenRepository
import javax.inject.Inject

class ClearLocalTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke() {
        repository.clearLocalToken()
    }
}