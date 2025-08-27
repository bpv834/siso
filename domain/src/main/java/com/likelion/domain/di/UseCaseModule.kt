package com.likelion.domain.di

import com.likelion.domain.call_for_caller.repository.CallRepository
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.login.repository.UserSignUpRepository
import com.likelion.domain.login.usecase.ClearTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.RegisterProfileToServerUseCase
import com.likelion.domain.login.usecase.SaveTemporaryUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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


    @Provides
    @Singleton
    fun provideClearTemporaryUserProfileUseCase(
        userRepository: UserSignUpRepository
    ): ClearTemporaryUserProfileUseCase {
        return ClearTemporaryUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetTemporaryUserProfileUseCase(
        userRepository: UserSignUpRepository
    ): GetTemporaryUserProfileUseCase {
        return GetTemporaryUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterProfileToServerUseCase(
        userRepository: UserSignUpRepository
    ): RegisterProfileToServerUseCase {
        return RegisterProfileToServerUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSaveTemporaryUserProfileUseCase(
        userRepository: UserSignUpRepository
    ): SaveTemporaryUserProfileUseCase {
        return SaveTemporaryUserProfileUseCase(userRepository)
    }
}