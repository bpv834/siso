package com.likelion.domain.call.repository

import com.likelion.domain.call.model.UserProfileModel

interface ProfileRepository {
    suspend fun getUserProfile(accessToken: String, userId: Long): Result<UserProfileModel>
    suspend fun getMyProfile(accessToken: String): Result<UserProfileModel>

}