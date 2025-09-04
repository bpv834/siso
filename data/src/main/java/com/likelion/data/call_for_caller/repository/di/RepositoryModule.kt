package com.likelion.data.call_for_caller.repository.di

import com.likelion.data.call_for_caller.repository.CallRepositoryImpl
import com.likelion.data.call_for_caller.repository.ProfileRepositoryImpl
import com.likelion.data.home.repository.UserRepositoryImpl
import com.likelion.domain.call_for_caller.repository.CallRepository
import com.likelion.domain.call_for_caller.repository.ProfileRepository
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
    abstract fun bindCallRepository(
        callRepositoryImpl: CallRepositoryImpl
    ): CallRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    // Note: FakeImagesApi, FakeInterestApi 등과 같은 외부 API들은 @Provides로 별도 제공되어야 합니다.
    // 이는 @Binds의 범위를 벗어납니다.
    // 예를 들어, FakeImagesApi를 제공하는 모듈은 다음과 같이 정의될 수 있습니다.
    /*
    @Provides
    @Singleton
    fun provideFakeImagesApi(): FakeImagesApi {
        return FakeImagesApi()
    }
    */
}