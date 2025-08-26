package com.likelion.domain.mypage.di

import com.likelion.domain.mypage.repository.APILocationRepository
import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.CurrentLocationSetUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationUseCaseModule {
    
    @Provides
    @Singleton
    fun provideTopLocationUseCase(locationRepository: LocationRepository): TopLocationUseCase =
        TopLocationUseCase(locationRepository)

    @Provides
    @Singleton
    fun provideBottomLocationUseCase(locationRepository: LocationRepository): BottomLocationUseCase =
        BottomLocationUseCase(locationRepository)

    @Provides
    @Singleton
    fun provideCurrentLocationSetUseCase(apiLocationRepository: APILocationRepository): CurrentLocationSetUseCase =
        CurrentLocationSetUseCase(apiLocationRepository)
}