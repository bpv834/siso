package com.likelion.remote.di

import com.likelion.remote.api.CallApiService
import com.likelion.remote.api.FcmApiService
import com.likelion.remote.api.ImageApiService
import com.likelion.remote.api.ChatApiService
import com.likelion.remote.api.InterestApiService
import com.likelion.remote.api.KakaoAuthApiService
import com.likelion.remote.api.MatchingApiService
import com.likelion.remote.api.UserApiService
import com.likelion.remote.api.VoiceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideKakaoAuthApiService(retrofit: Retrofit): KakaoAuthApiService =
        retrofit.create(KakaoAuthApiService::class.java)

    @Singleton
    @Provides
    fun provideChatApiService(
        retrofit: Retrofit
    ): ChatApiService = retrofit.create(ChatApiService::class.java)

    // CallApiService를 제공하는 부분 (Fake 구현체 주입)
    @Singleton
    @Provides
    fun provideCallApiService(
        // 이 모듈은 Retrofit 인스턴스를 주입받아 실제 API 서비스를 만들지만,
        // 현재는 FakeCallApiService를 사용하기 위해 Retrofit 매개변수를 주석 처리합니다.
        // 실제 서버 통신 시에는 retrofit: Retrofit 매개변수를 활성화하세요.
        retrofit: Retrofit
    ): CallApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        // return FakeCallApiService() // 👈  가짜 구현체 반환
        return retrofit.create(CallApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideUserApiService(
        // 이 모듈은 Retrofit 인스턴스를 주입받아 실제 API 서비스를 만들지만,
        // 현재는 FakeCallApiService를 사용하기 위해 Retrofit 매개변수를 주석 처리합니다.
        // 실제 서버 통신 시에는 retrofit: Retrofit 매개변수를 활성화하세요.
        retrofit: Retrofit
    ): UserApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return retrofit.create(UserApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideImageApiService(
        retrofit: Retrofit
    ): ImageApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return retrofit.create(ImageApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideVoiceSampleApiService(
        retrofit: Retrofit
    ): VoiceApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return retrofit.create(VoiceApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideMatchingUserApiService(
        retrofit: Retrofit
    ): MatchingApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return retrofit.create(MatchingApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideFcmApiServiceApiService(
        retrofit: Retrofit
    ): FcmApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return retrofit.create(FcmApiService::class.java) // 실제 서버 통신 시 사용
    }

    @Singleton
    @Provides
    fun provideInterestApiService(
        retrofit: Retrofit
    ): InterestApiService {
        // 관심사 목록 API를 제공하는 서비스를 생성합니다.
        return retrofit.create(InterestApiService::class.java)
    }
}