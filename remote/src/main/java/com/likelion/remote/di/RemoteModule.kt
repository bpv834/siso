package com.likelion.remote.di

import com.likelion.remote.api.KakaoAuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideKakaoAuthApiService(retrofit: Retrofit): KakaoAuthApiService =
        retrofit.create(KakaoAuthApiService::class.java)
}