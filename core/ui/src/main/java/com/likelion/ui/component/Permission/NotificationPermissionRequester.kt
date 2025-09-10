package com.likelion.ui.component.Permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        // Android 13(TIRAMISU) 이상 버전에서만 POST_NOTIFICATIONS 권한 체크 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // 권한이 아직 허용되지 않았다면
            if (!notificationPermissionState.status.isGranted) {
                // 권한 요청 다이얼로그를 띄움
                notificationPermissionState.launchPermissionRequest()
            } else {
                // 이미 허용되어 있는 상태라면, 바로 허용 콜백 실행
                onPermissionGranted()
            }
        } else {
            // Android 12 이하 버전은 POST_NOTIFICATIONS 권한이 필요 없으므로
            // 자동으로 허용된 것으로 간주하고 콜백 실행
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
