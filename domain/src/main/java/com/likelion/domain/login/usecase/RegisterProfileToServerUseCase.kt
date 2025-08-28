package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserSignUpRepository
import javax.inject.Inject

class RegisterProfileToServerUseCase @Inject constructor(
    private val repository: UserSignUpRepository
) {
    suspend fun execute(refreshToken: String, profile: UserSignUpProfile) {
        repository.registerProfileToServer(refreshToken, profile)
    }
}