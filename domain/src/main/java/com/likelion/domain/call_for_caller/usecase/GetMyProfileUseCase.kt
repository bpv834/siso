package com.likelion.domain.call_for_caller.usecase

import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.domain.call_for_caller.repository.ProfileRepository
import javax.inject.Inject


class GetMyProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(accessToken: String): Result<UserProfileModel> {
        return profileRepository.getMyProfile(accessToken = accessToken)
    }
}
