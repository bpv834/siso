package com.likelion.domain.onboarding.usecase

import com.likelion.domain.onboarding.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardingSkipUseCase @Inject constructor(
    private val repository: OnBoardingRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.getOnBoardingSkip()
    }
}