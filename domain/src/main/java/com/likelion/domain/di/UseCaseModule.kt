package com.likelion.domain.di

import com.likelion.domain.home.repository.CallRepository
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.home.usecase.ObserveCallEventsUseCase
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

    @Provides
    @Singleton
    fun provideObserveCallEventsUseCase(
        callRepository: CallRepository // DI 컨테이너가 제공 data 단에서 @binds 에서 제공중
    ): ObserveCallEventsUseCase {
        return ObserveCallEventsUseCase(callRepository)
    }
}