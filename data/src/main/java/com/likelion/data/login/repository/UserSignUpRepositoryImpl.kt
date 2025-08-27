package com.likelion.data.login.repository

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserSignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryUserSignUpRepositoryImpl @Inject constructor() : UserSignUpRepository {

    private var temporaryProfile = UserSignUpProfile()

    override suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile) {
        temporaryProfile = profile
    }

    override suspend fun getTemporaryUserProfile(): UserSignUpProfile = temporaryProfile

    override suspend fun clearTemporaryUserProfile() {
        temporaryProfile = UserSignUpProfile()
    }

    override suspend fun registerProfileToServer(profile: UserSignUpProfile) {
        TODO("Not yet implemented")
    }

}