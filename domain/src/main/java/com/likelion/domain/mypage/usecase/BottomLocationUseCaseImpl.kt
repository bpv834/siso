package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.repository.LocationRepository
import javax.inject.Inject

class BottomLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): BottomLocationUseCase {
    override fun invoke(topName: String): List<Location> =
        locationRepository.getBottomLocationList(topName)

}