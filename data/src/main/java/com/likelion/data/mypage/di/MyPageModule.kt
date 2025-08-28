package com.likelion.data.mypage.di

import com.likelion.data.mypage.repository.UserFullRepositoryImpl
import com.likelion.domain.mypage.repository.UserFullRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyPageModule {

    @Binds
    @Singleton
    abstract fun bindUserFullRepository(impl: UserFullRepositoryImpl): UserFullRepository

}