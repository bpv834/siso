package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.User
import com.likelion.domain.login.repository.LoginRepository
import javax.inject.Inject

class PostRefreshTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(token: BasicToken): User {
        return repository.postRefreshToken(token)
    }
}