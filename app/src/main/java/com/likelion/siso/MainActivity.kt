package com.likelion.siso

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.likelion.kakao_auth.repository.CurrentActivityHolder
import com.likelion.siso.navigation.SisoApp
import com.likelion.ui.component.Permission.AudioPermissionRequester
import com.likelion.ui.theme.SisoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var activityHolder: CurrentActivityHolder
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHolder.set(this)
        enableEdgeToEdge()
        setContent {
            AudioPermissionRequester()

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
