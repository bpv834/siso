package com.likelion.domain.call.usecase

import com.likelion.domain.call.model.UserProfileModel
import com.likelion.domain.call.repository.ProfileRepository
import javax.inject.Inject


class GetMyProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(accessToken: String): Result<UserProfileModel> {
        return profileRepository.getMyProfile(accessToken = accessToken)
    }
}
