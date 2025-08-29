package com.likelion.data.login.repository

import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.InMemoryUserSignUpRepository
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.remote.api.UserSignUpApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryUserSignUpRepositoryImpl @Inject constructor(
    private val userSignUpApi: UserSignUpApi, // Retrofit API 서비스 주입
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
) : InMemoryUserSignUpRepository {

    private var temporaryProfile = UserSignUpProfile()

    override suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile) {
        temporaryProfile = profile
    }

    override suspend fun getTemporaryUserProfile(): UserSignUpProfile = temporaryProfile

    override suspend fun clearTemporaryUserProfile() {
        temporaryProfile = UserSignUpProfile()
    }

}

