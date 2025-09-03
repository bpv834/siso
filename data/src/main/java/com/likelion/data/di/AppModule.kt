package com.likelion.data.di

import android.content.Context
import com.likelion.data.mypage.repository.APILocationRepositoryImpl
import com.likelion.data.mypage.repository.LocationRepositoryImpl
import com.likelion.domain.mypage.repository.APILocationRepository
import com.likelion.domain.mypage.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocationRepo(@ApplicationContext context: Context): LocationRepository =
        LocationRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideLocationRepository(@ApplicationContext context: Context): APILocationRepository =
        APILocationRepositoryImpl(context)
}