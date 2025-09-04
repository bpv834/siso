package com.likelion.data.home.repository.di

import com.likelion.data.home.repository.HomePageRepositoryImpl
import com.likelion.data.home.repository.UserRepositoryImpl
import com.likelion.domain.home.repository.HomePageRepository
import com.likelion.domain.home.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsersRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UsersRepository

    @Binds
    @Singleton
    abstract fun bindHomePageRepository(
        impl: HomePageRepositoryImpl
    ): HomePageRepository
}