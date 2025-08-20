package com.likelion.data.home.repository.di

import com.likelion.data.home.api.test.FakeImagesApi
import com.likelion.data.home.api.test.FakeInterestApi
import com.likelion.data.home.api.test.FakeProfileApi
import com.likelion.data.home.api.test.FakeUserApi
import com.likelion.data.home.api.test.FakeVoiceApi
import com.likelion.data.home.repository.CallRepositoryImpl
import com.likelion.data.home.repository.UserRepository2Impl2
import com.likelion.domain.home.repository.CallRepository
import com.likelion.domain.home.repository.UsersRepository2
import com.likelion.network.util.AgoraVoiceManager
import com.likelion.remote.api.CallApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule2 {
    @Provides
    @Singleton
    fun provideUsersRepository(
        userApi: FakeUserApi,
        profileApi: FakeProfileApi,
        imagesApi: FakeImagesApi,
        voiceApi: FakeVoiceApi,
        interestApi: FakeInterestApi
    ): UsersRepository2 { // Domain 계층의 인터페이스를 반환
        // 리턴타입은 domain 의 repo 이지만 리턴은 repo를 impl 한 data계층을 리턴한다.
        return UserRepository2Impl2(userApi, profileApi, imagesApi, voiceApi, interestApi)
    }

    @Singleton
    @Provides
    fun provideCallRepository(
        agoraVoiceManager: AgoraVoiceManager, // data / di 에서 주입
        callApiService: CallApiService // remote / di 에서 주입
    ): CallRepository {
        // 이제 CallRepositoryImpl은 AgoraVoiceManager와 CallApiService를 모두 주입받습니다.
        return CallRepositoryImpl(agoraVoiceManager, callApiService)
    }

}