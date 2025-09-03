package com.likelion.siso

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
        activityHolder.set(this)
        enableEdgeToEdge()
        setContent {
            // ✅ 알림 권한 요청 실행
            NotificationPermissionRequester(
                onPermissionGranted = { /* 알림 허용됨 → FCM 정상 동작 */ },
                onPermissionDenied = { /* 알림 거부됨 → 안내 UI 보여주기 */ }
            )
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

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun NotificationPermissionRequester(
        onPermissionGranted: () -> Unit = {},
        onPermissionDenied: () -> Unit = {}
    ) {
        // POST_NOTIFICATIONS 권한 상태 관리
        val notificationPermissionState = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // 권한이 아직 없으면 요청
                if (!notificationPermissionState.status.isGranted) {
                    notificationPermissionState.launchPermissionRequest()
                } else {
                    onPermissionGranted()
                }
            } else {
                // 12 이하 기기는 자동 허용
                onPermissionGranted()
            }
        }

        when (notificationPermissionState.status) {
            is PermissionStatus.Granted -> {
                onPermissionGranted()
            }
            is PermissionStatus.Denied -> {
                onPermissionDenied()
            }
        }
    }


}
