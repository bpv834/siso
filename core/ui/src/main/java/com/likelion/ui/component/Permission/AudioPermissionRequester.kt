package com.likelion.ui.component.Permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * A composable function to handle runtime permission requests for the Agora SDK.
 * 아고라 SDK에 필요한 런타임 권한 요청을 처리하는 컴포저블 함수입니다.
 *
 * @param onPermissionGranted The callback to be invoked when the RECORD_AUDIO permission is granted.
 * RECORD_AUDIO 권한이 허용되었을 때 호출될 콜백입니다.
 * @param onPermissionDenied The callback to be invoked when the RECORD_AUDIO permission is denied.
 * RECORD_AUDIO 권한이 거부되었을 때 호출될 콜백입니다.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AudioPermissionRequester(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    // 마이크(RECORD_AUDIO) 권한 상태를 관리합니다.
    val audioPermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )

    LaunchedEffect(Unit) {
        // 컴포넌트가 처음 로드될 때 권한을 확인하고, 없으면 요청합니다.
        if (!audioPermissionState.status.isGranted) {
            audioPermissionState.launchPermissionRequest()
        }
    }

    when (audioPermissionState.status) {
        is PermissionStatus.Granted -> {
            // 권한이 허용된 상태. 콜백을 호출합니다.
            onPermissionGranted()
        }
        is PermissionStatus.Denied -> {
            // 권한이 거부된 상태. 콜백을 호출합니다.
            onPermissionDenied()
        }
    }
}
