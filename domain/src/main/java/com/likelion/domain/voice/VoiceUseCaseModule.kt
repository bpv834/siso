package com.likelion.domain.voice

import com.likelion.domain.voice.repository.VoiceRepository
import com.likelion.domain.voice.usecase.GetVoiceSampleUseCase
import com.likelion.domain.voice.usecase.UploadVoiceSampleUseCase
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
    fun provideUploadVoiceSampleUseCase(
        repository: VoiceRepository
    ): UploadVoiceSampleUseCase {
        return UploadVoiceSampleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVoiceSampleUseCase(
        repository: VoiceRepository
    ): GetVoiceSampleUseCase {
        return GetVoiceSampleUseCase(repository)
    }



}