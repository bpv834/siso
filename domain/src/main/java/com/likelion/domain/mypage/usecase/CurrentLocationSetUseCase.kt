package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.LocationState
import com.likelion.domain.mypage.repository.APILocationRepository
import com.likelion.domain.mypage.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class CurrentLocationSetUseCase(
    private val locationRepository: APILocationRepository
) {
    fun invoke(): Flow<LocationState> =
        locationRepository.getLocationFlow()
}