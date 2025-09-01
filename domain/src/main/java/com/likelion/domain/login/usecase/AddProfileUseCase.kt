package com.likelion.domain.login.usecase

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserProfileRepository
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(
    private val repository : UserProfileRepository
) {
    suspend fun execute(accessToken : String, user : UserSignUpProfile){
        repository.addProfile(accessToken = accessToken, profile = user)
    }

}
