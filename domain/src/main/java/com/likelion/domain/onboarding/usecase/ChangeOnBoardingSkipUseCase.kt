package com.likelion.domain.onboarding.usecase

import com.likelion.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class ChangeOnBoardingSkipUseCase @Inject constructor(
    private val repository: OnBoardingRepository
) {
    suspend operator fun invoke(isSkip: Boolean) {
        repository.changeOnBoardingSkip(isSkip)
    }
}