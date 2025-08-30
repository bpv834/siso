package com.likelion.domain.voice

import com.likelion.domain.call_for_caller.repository.CallRepository
import com.likelion.domain.call_for_caller.usecase.ObserveCallEventsUseCase
import com.likelion.domain.home.repository.UsersRepository
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.image.repository.ImageRepository
import com.likelion.domain.image.usecase.UploadImageUseCase
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
import com.likelion.domain.voice.repository.VoiceRepository
import com.likelion.domain.voice.usecase.GetVoiceSampleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceUseCaseModule {
    @Provides
    @Singleton
    fun provideUploadImageUseCase(
        repository: ImageRepository
    ): UploadImageUseCase {
        return UploadImageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVoiceSampleUseCase(
        repository: VoiceRepository
    ): GetVoiceSampleUseCase {
        return GetVoiceSampleUseCase(repository)
    }



}