package com.likelion.siso

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // 디버그 모드일 때만 로그 찍도록
            Timber.plant(Timber.DebugTree())
        }

        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}