package com.likelion.data.onboarding.repository

import com.likelion.domain.onboarding.repository.OnBoardingRepository
import com.likelion.local.datastore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val data: DataStoreDataSource
) : OnBoardingRepository {
    override suspend fun changeOnBoardingSkip(isSkip: Boolean) {
        data.changeOnBoardingSkip(isSkip)
    }

    override suspend fun getOnBoardingSkip(): Flow<Boolean> {
        return data.getOnBoardingSkip()
    }

}