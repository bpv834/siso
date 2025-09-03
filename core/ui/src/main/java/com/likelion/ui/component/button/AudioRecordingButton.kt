package com.likelion.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AudioRecordingButton(
    // 녹음 시작 시 실행할 액션을 콜백으로 받습니다.
    onRecordAction: () -> Unit
) {
    val recordAudioPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.RECORD_AUDIO
    )

    CommonActiveButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        text = "녹음 시작",
        onClick = {
            if (recordAudioPermissionState.status.isGranted) {
                // 권한이 있으면, 전달받은 액션을 실행합니다.
                onRecordAction()
            } else {
                // 권한이 없으면, 권한 요청을 시작합니다.
                recordAudioPermissionState.launchPermissionRequest()
            }
        }
    )
}