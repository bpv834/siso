package com.likelion.data.mypage.di

import com.likelion.data.mypage.repository.LocationRepositoryImpl
import com.likelion.domain.mypage.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyPageModule {
//    @Binds
//    @Singleton
//    fun provideLocationMapper(): LocationMapper =
//        LocationMapper()
//
//    @Binds
//    @Singleton
//    fun provideMyPageRepository(
//        locationMapper: LocationMapper
//    ): LocationRepository = LocationRepositoryImpl(locationMapper)

    @Binds
    @Singleton
    abstract fun bindLocationRepo(impl: LocationRepositoryImpl): LocationRepository

}