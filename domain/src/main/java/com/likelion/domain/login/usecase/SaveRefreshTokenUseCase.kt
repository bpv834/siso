package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.repository.TokenRepository
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(token: BasicToken) {
        repository.saveRefreshToken(token)
    }
}