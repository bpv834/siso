package com.likelion.siso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.likelion.kakao_auth.repository.CurrentActivityHolder
import com.likelion.siso.navigation.SisoApp
import com.likelion.ui.theme.SisoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var activityHolder: CurrentActivityHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SisoTheme {
                SisoApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activityHolder.set(this)
    }
}
