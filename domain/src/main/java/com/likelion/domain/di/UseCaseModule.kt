package com.likelion.domain.di

import com.likelion.domain.call_for_caller.repository.CallRepository
import com.likelion.domain.call_for_caller.repository.ProfileRepository
import com.likelion.domain.call_for_caller.usecase.DenyCallUseCase
import com.likelion.domain.call_for_caller.usecase.EvaluationAfterCallUseCase
import com.likelion.domain.call_for_caller.usecase.GetMyProfileUseCase
import com.likelion.domain.call_for_caller.usecase.GetUserProfileUseCase
import com.likelion.domain.call_for_caller.usecase.LeaveChannelUseCase
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.call_for_caller.usecase.RejectCallUseCase
import com.likelion.domain.call_for_caller.usecase.ToggleMuteUseCase
import com.likelion.domain.call_for_caller.usecase.ToggleSpeakerUseCase
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.login.repository.InMemoryUserSignUpRepository
import com.likelion.domain.login.repository.TokenRepository
import com.likelion.domain.login.repository.UserProfileRepository
import com.likelion.domain.login.usecase.AddProfileUseCase
import com.likelion.domain.login.usecase.ClearTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.SaveTemporaryUserProfileUseCase
import com.likelion.domain.notification.repository.FcmTokenRepository
import com.likelion.domain.notification.usecase.GetFcmTokenUseCase
import com.likelion.domain.notification.usecase.SaveFcmTokenUseCase
import com.likelion.domain.notification.usecase.SendFcmTokenUseCase
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
        userRepository: InMemoryUserSignUpRepository
    ): ClearTemporaryUserProfileUseCase {
        return ClearTemporaryUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetTemporaryUserProfileUseCase(
        userRepository: InMemoryUserSignUpRepository
    ): GetTemporaryUserProfileUseCase {
        return GetTemporaryUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSaveTemporaryUserProfileUseCase(
        userRepository: InMemoryUserSignUpRepository
    ): SaveTemporaryUserProfileUseCase {
        return SaveTemporaryUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSendFcmTokenUseCase(
        fcmTokenRepository: FcmTokenRepository
    ): SendFcmTokenUseCase {
        return SendFcmTokenUseCase(fcmTokenRepository)
    }

    @Provides
    @Singleton
    fun provideSaveFcmTokenUseCase(
        tokenRepository: TokenRepository
    ): SaveFcmTokenUseCase {
        return SaveFcmTokenUseCase(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideGetFcmTokenUseCase(
        tokenRepository: TokenRepository
    ): GetFcmTokenUseCase {
        return GetFcmTokenUseCase(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideAddProfileUseCase(
        repository: UserProfileRepository
    ): AddProfileUseCase {
        return AddProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRejectCallUseCase(
        repository: CallRepository
    ): RejectCallUseCase {
        return RejectCallUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDenyCallUseCase(
        repository: CallRepository
    ): DenyCallUseCase {
        return DenyCallUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideEvaluationAfterCallUseCase(
        repository: CallRepository
    ): EvaluationAfterCallUseCase {
        return EvaluationAfterCallUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLeaveChannelUseCase(
        repository: CallRepository
    ): LeaveChannelUseCase {
        return LeaveChannelUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleSpeakerUseCase(
        repository: CallRepository
    ): ToggleSpeakerUseCase {
        return ToggleSpeakerUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleMuteUseCase(
        repository: CallRepository
    ): ToggleMuteUseCase {
        return ToggleMuteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        repository: ProfileRepository
    ): GetUserProfileUseCase {
        return GetUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMyProfileUseCase(
        repository: ProfileRepository
    ): GetMyProfileUseCase {
        return GetMyProfileUseCase(repository)
    }




}