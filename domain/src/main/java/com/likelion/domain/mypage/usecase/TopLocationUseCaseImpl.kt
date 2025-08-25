package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class TopLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): TopLocationUseCase {
    override fun invoke(): Location =
        locationRepository.getTopLocationList()

}