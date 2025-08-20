package com.likelion.data.home.api.test

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiTestModule2 {

    @Provides
    @Singleton
    fun provideFakeUserApi(): FakeUserApi = FakeUserApi()

    @Provides
    @Singleton
    fun provideFakeProfileApi(): FakeProfileApi = FakeProfileApi()

    @Provides
    @Singleton
    fun provideFakeImagesApi(): FakeImagesApi = FakeImagesApi()

    @Provides
    @Singleton
    fun provideFakeVoiceApi(): FakeVoiceApi = FakeVoiceApi()

    @Provides
    @Singleton
    fun provideFakeInterestApi(): FakeInterestApi = FakeInterestApi()
}