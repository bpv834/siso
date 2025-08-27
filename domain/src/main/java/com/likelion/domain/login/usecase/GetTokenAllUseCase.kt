package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.User
import com.likelion.domain.login.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenAllUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(): Flow<User?> {
        return repository.getTokenAll()
    }
}