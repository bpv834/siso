package com.likelion.domain.mypage.repository

import com.likelion.domain.mypage.LocationState
import kotlinx.coroutines.flow.Flow

interface APILocationRepository {
    fun getLocationFlow() : Flow<LocationState>
}