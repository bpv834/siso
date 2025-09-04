package com.likelion.data.call_for_caller.repository

import com.likelion.data.call_for_caller.mapper.toDomain
import com.likelion.domain.call_for_caller.model.UserProfileModel
import com.likelion.domain.call_for_caller.repository.ProfileRepository
import com.likelion.remote.api.UserApiService
import com.likelion.remote.model.response.UserProfileResponse
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : ProfileRepository {
    override suspend fun getUserProfile(userId: Long): UserProfileModel {
        val profileResponse : UserProfileResponse =  userApiService.getUserProfile(userId)
        val result = profileResponse.toDomain()

        return result
    }
}