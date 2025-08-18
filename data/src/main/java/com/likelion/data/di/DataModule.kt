package com.likelion.data.di

import com.likelion.data.login.repository.LoginRepositoryImpl
import com.likelion.data.login.repository.TokenRepositoryImpl
import com.likelion.domain.login.repository.AuthTokenRepository
import com.likelion.domain.login.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 구현체 연결부
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindAuthTokenRepository(
        impl: TokenRepositoryImpl
    ): AuthTokenRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository
}