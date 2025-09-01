package com.likelion.domain.login.repository

import com.likelion.domain.login.model.UserSignUpProfile

interface UserProfileRepository {
    suspend fun addProfile(
        accessToken: String,
        profile: UserSignUpProfile
    )

}