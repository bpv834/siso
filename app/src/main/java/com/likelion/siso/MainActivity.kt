package com.likelion.siso

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import com.likelion.kakao_auth.repository.CurrentActivityHolder
import com.likelion.siso.navigation.SisoApp
import com.likelion.ui.theme.SisoTheme
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject
import kotlin.io.encoding.ExperimentalEncodingApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var activityHolder: CurrentActivityHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHolder.set(this)
        enableEdgeToEdge()
        setContent {
            SisoTheme {
                SisoApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            activityHolder.clear()
        }
    }
}

