package com.likelion.data.mypage.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.likelion.data.mypage.repository.APILocationRepositoryImpl
import com.likelion.data.mypage.repository.LocationRepositoryImpl
import com.likelion.data.mypage.repository.UserFullRepositoryImpl
import com.likelion.domain.mypage.repository.APILocationRepository
import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.repository.UserFullRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Binds
    @Singleton
    abstract fun provideLocationRepository(impl: APILocationRepositoryImpl): APILocationRepository

    @Binds
    @Singleton
    abstract fun provideUserFullRepository(impl: UserFullRepositoryImpl): UserFullRepository

}