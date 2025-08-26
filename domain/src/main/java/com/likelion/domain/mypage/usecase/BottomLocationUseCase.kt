package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class BottomLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(topName: String): Location =
        locationRepository.getBottomLocationList(topName)

}