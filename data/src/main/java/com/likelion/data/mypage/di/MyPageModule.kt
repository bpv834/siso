package com.likelion.data.mypage.di

import com.likelion.data.mypage.mapper.LocationMapper
import com.likelion.data.mypage.repository.LocationRepositoryImpl
import com.likelion.domain.mypage.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyPageModule {
    @Provides
    @Singleton
    fun provideLocationMapper(): LocationMapper =
        LocationMapper()

    @Provides
    @Singleton
    fun provideMyPageRepository(
        locationMapper: LocationMapper
    ): LocationRepository = LocationRepositoryImpl(locationMapper)
}