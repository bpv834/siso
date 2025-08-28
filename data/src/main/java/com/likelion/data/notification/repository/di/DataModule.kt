package com.likelion.data.notification.repository.di

import com.likelion.data.login.repository.InMemoryUserSignUpRepositoryImpl
import com.likelion.data.login.repository.LoginRepositoryImpl
import com.likelion.data.login.repository.TokenRepositoryImpl
import com.likelion.data.notification.repository.FcmTokenRepositoryImpl
import com.likelion.domain.login.repository.LoginRepository
import com.likelion.domain.login.repository.TokenRepository
import com.likelion.domain.login.repository.UserSignUpRepository
import com.likelion.domain.notification.repository.FcmTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 구현체 연결부
@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationDataModule {

    @Binds
    @Singleton
    abstract fun bindFcmTokenRepository(
        impl : FcmTokenRepositoryImpl
    ) :  FcmTokenRepository
}