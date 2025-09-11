package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.UserProfileModel
import com.likelion.domain.call.repository.ProfileRepository

import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(accessToken: String, userId: Long): Result<UserProfileModel> {
        return profileRepository.getUserProfile(accessToken = accessToken, userId = userId)
    }
}
