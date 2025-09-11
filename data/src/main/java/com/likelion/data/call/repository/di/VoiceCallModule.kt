package com.likelion.data.call.repository.di

import android.content.Context
import com.likelion.network.util.AgoraVoiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt 모듈: Agora 실시간 음성 통화 관련 의존성을 제공합니다.
 * 이 모듈은 SingletonComponent에 설치되어, 앱 생명주기 동안 단일 인스턴스를 보장합니다.
 */
@Module
@InstallIn(SingletonComponent::class)
object VoiceCallModule {

    /**
     * AgoraVoiceManager의 단일 인스턴스를 제공합니다.
     *
     * @param context Hilt가 자동으로 주입하는 애플리케이션 Context
     * @return AgoraVoiceManager의 싱글톤 인스턴스
     */
    @Provides
    @Singleton
    fun provideAgoraVoiceManager(
        @ApplicationContext context: Context
    ): AgoraVoiceManager {
        val appId = "a914eda873c04f09a72ee7bd3e522300"
        return AgoraVoiceManager(context, appId)
    }
}