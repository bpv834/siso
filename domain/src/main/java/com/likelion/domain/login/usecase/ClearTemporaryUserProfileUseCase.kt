package com.likelion.domain.login.usecase

import com.likelion.domain.login.repository.UserSignUpRepository
import javax.inject.Inject

class ClearTemporaryUserProfileUseCase @Inject constructor(
    private val repository: UserSignUpRepository
) {
    suspend fun execute() {
        return repository.clearTemporaryUserProfile()
    }
}