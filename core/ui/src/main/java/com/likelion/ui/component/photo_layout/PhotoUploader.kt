package com.likelion.ui.component.photo_layout

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.likelion.ui.component.bottomSheet.PhotoUploadBottomSheet
import com.likelion.ui.component.camera.CameraPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoUploader(
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onImageCaptured: (Bitmap) -> Unit,
    onImagePicked: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    var isCameraVisible by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) isCameraVisible = true
        // else 권한 거부 처리
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        // 앨범에서 가져온 uri를 비트맵으로 변환
        uri?.let { onImagePicked(it.getBitmap(context)!!) }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            PhotoUploadBottomSheet(
                onDismissRequest = onDismissRequest,
                onTakePhotoClick = {
                    onDismissRequest()
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
                onPickFromGalleryClick = {
                    onDismissRequest()
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }

    if (isCameraVisible) {
        CameraPreview(
            onImageCaptured = { bitmap ->
                onImageCaptured(bitmap)
                isCameraVisible = false
            }
        )
    }
}

// uri를 비트맵으로 변환하는 확장 함수
fun Uri.getBitmap(context: Context): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}