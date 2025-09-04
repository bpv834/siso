package com.likelion.domain.onboarding.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun changeOnBoardingSkip(isSkip: Boolean)
    suspend fun getOnBoardingSkip(): Flow<Boolean>
}