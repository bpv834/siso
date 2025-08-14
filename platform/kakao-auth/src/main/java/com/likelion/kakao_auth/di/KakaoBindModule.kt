package com.likelion.kakao_auth.di

import com.kakao.sdk.user.UserApiClient
import com.likelion.domain.auth.provider.KakaoTokenProvider
import com.likelion.kakao_auth.repository.KakaoTokenProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoBindModule {
    @Binds
    abstract fun bindKakaoProviderImpl(
        impl: KakaoTokenProviderImpl
    ): KakaoTokenProvider
}

@Module
@InstallIn(SingletonComponent::class)
object KakaoProvieModule {
    @Provides
    @Singleton
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance
}