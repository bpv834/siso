package com.likelion.siso

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
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
        Log.d("TEST", Utility.getKeyHash(this))
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}