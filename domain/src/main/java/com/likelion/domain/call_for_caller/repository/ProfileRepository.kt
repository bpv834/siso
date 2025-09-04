package com.likelion.domain.call_for_caller.repository

import com.likelion.domain.call_for_caller.model.UserProfileModel

interface ProfileRepository {
    suspend fun getUserProfile(userId: Long): Result<UserProfileModel>
}