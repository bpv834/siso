package com.likelion.domain.login.usecase

import com.likelion.domain.login.repository.AuthTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(
    private val repository: AuthTokenRepository
) {
    suspend operator fun invoke(): Flow<String?> {
        return repository.getAuthToken()
    }
}