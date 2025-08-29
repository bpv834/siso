package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.InMemoryUserSignUpRepository
import com.likelion.domain.login.repository.SignUpRepository
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(
    private val repository : SignUpRepository
) {
    suspend fun execute(refreshToken : String, user : UserSignUpProfile){
        repository.addProfile(refreshToken = refreshToken, profile = user)
    }

}
