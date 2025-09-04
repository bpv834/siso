package com.likelion.data.onboarding.di

import com.likelion.data.onboarding.repository.OnBoardingRepositoryImpl
import com.likelion.domain.onboarding.repository.OnBoardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnBoardingDataModule {
    @Binds
    @Singleton
    abstract fun bindOnBoardingRepository(
        impl: OnBoardingRepositoryImpl
    ): OnBoardingRepository
}