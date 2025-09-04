package com.likelion.domain.call_for_caller.repository

import com.likelion.domain.call_for_caller.model.UserProfileModel

interface ProfileRepository {
    suspend fun getUserProfile(accessToken: String, userId: Long): Result<UserProfileModel>
}