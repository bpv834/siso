package com.likelion.domain.login.repository

import com.likelion.domain.login.model.UserSignUpProfile

interface InMemoryUserSignUpRepository {

    suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile)
    suspend fun getTemporaryUserProfile(): UserSignUpProfile
    suspend fun clearTemporaryUserProfile()

}