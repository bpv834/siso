package com.likelion.domain.di2

import com.likelion.domain.repository.UsersRepository
import com.likelion.domain.usecase.GetAllUsersUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetAllUsersUseCase(
        usersRepository: UsersRepository // DI 컨테이너가 제공
    ): GetAllUsersUseCase {
        return GetAllUsersUseCase(usersRepository)
    }
}