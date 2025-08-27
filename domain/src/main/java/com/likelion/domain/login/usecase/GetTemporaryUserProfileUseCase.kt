package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserSignUpRepository
import javax.inject.Inject

class GetTemporaryUserProfileUseCase @Inject constructor(
    private val repository: UserSignUpRepository
) {
    suspend  fun execute(): UserSignUpProfile =
        repository.getTemporaryUserProfile()
}