package com.likelion.siso

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TEST", Utility.getKeyHash(this))
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}