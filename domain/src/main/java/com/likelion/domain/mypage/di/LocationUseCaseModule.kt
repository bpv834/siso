package com.likelion.domain.mypage.di

import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.BottomLocationUseCaseImpl
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCaseImpl
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
        TopLocationUseCaseImpl(locationRepository)

    @Provides
    @Singleton
    fun provideBottomLocationUseCase(locationRepository: LocationRepository): BottomLocationUseCase =
        BottomLocationUseCaseImpl(locationRepository)
}