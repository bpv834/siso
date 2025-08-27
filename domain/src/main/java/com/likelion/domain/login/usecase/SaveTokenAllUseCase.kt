package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.User
import com.likelion.domain.login.repository.TokenRepository
import javax.inject.Inject

class SaveTokenAllUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(user: User) {
        repository.saveTokenAll(user)
    }
}