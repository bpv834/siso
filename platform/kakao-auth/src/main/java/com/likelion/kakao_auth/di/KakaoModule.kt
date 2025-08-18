package com.likelion.kakao_auth.di

import com.likelion.domain.auth.repository.KakaoAuthRepository
import com.likelion.kakao_auth.repository.KakaoAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoAuthModule {
    @Binds
    abstract fun bindKakaoAuthRepository(
        impl: KakaoAuthRepositoryImpl
    ): KakaoAuthRepository
}