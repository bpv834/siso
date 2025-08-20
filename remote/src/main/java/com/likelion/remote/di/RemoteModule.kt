package com.likelion.remote.di

import com.likelion.remote.api.CallApiService
import com.likelion.remote.api.KakaoAuthApiService
import com.likelion.remote.fake_api.FakeCallApiService
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

    // CallApiService를 제공하는 부분 (Fake 구현체 주입)
    @Singleton
    @Provides
    fun provideCallApiService(
        // 이 모듈은 Retrofit 인스턴스를 주입받아 실제 API 서비스를 만들지만,
        // 현재는 FakeCallApiService를 사용하기 위해 Retrofit 매개변수를 주석 처리합니다.
        // 실제 서버 통신 시에는 retrofit: Retrofit 매개변수를 활성화하세요.
        // retrofit: Retrofit
    ): CallApiService {
        // 실제 서버가 준비될 때까지 FakeCallApiService를 리턴합니다.
        return FakeCallApiService() // 👈 현재는 가짜 구현체 반환
        // return retrofit.create(CallApiService::class.java) // 실제 서버 통신 시 사용
    }
}