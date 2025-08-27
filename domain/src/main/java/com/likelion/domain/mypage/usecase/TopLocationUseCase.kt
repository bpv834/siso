package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class TopLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Location =
        locationRepository.getTopLocationList()

}