package com.likelion.data.voice.repository.di

import com.google.gson.annotations.Since
import com.likelion.data.voice.repository.VoiceRepositoryImpl
import com.likelion.domain.voice.repository.VoiceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VoiceDataModule{

    @Binds
    @Singleton
    abstract fun bindVoiceRepository (
        impl : VoiceRepositoryImpl
    ): VoiceRepository
}