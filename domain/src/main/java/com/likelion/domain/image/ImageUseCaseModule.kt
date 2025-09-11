package com.likelion.domain.image

import com.likelion.domain.image.repository.ImageRepository
import com.likelion.domain.image.usecase.GetImagesUseCase
import com.likelion.domain.image.usecase.GetPresignedImgsUseCase
import com.likelion.domain.image.usecase.UploadImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageUseCaseModule {


    @Provides
    @Singleton
    fun provideUploadImageUseCase(
        repository: ImageRepository
    ): UploadImageUseCase {
        return UploadImageUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetImageUseCase(
        repository: ImageRepository
    ): GetImagesUseCase {
        return GetImagesUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetPresignedImgUseCase(
        repository: ImageRepository
    ): GetPresignedImgsUseCase {
        return GetPresignedImgsUseCase(repository)
    }





}