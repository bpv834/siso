package com.likelion.domain.login.usecase

import com.likelion.domain.login.repository.InMemoryUserSignUpRepository
import javax.inject.Inject

class ClearTemporaryUserProfileUseCase @Inject constructor(
    private val repository: InMemoryUserSignUpRepository
) {
    suspend fun execute() {
        return repository.clearTemporaryUserProfile()
    }
}