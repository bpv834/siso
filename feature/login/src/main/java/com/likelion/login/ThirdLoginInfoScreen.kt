package com.likelion.login

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.camera.CameraPreview
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalMaterial3Api
fun ThirdLoginInfoScreen(
    viewModel: ThirdLoginInfoScreenViewModel = hiltViewModel()
) {
    val showBottomSheet by viewModel.showBottomSheet.collectAsStateWithLifecycle()
    // 💡 바텀 시트 상태 수정:
    // skipPartiallyExpanded = true: 부분 확장 상태를 건너뜁니다.
    // initialValue = SheetValue.Expanded: 바텀 시트가 처음부터 확장된 상태로 열립니다.
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val context = LocalContext.current

    var isCameraVisible by remember { mutableStateOf(false) }
    val capturedImages = remember { mutableStateListOf<Bitmap>() }

    // 카메라 권한 요청 런처
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 트루면 카메라 보여주는 상태를 트루로 변경한다.
            isCameraVisible = true
        } else {
            // 권한 거부 시 사용자에게 알림 로직
        }
    }

    // 갤러리 이미지 선택 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val bitmap = uri.getBitmap(context) // 확장 함수 사용
            if (capturedImages.size < 5 && bitmap != null) {
                capturedImages.add(bitmap)
            }
        }
    }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.size(size = 8.dp))
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_circle_bar_login3,
                contentDescription = "Step indicator",
            )
            Spacer(modifier = Modifier.size(size = 27.dp))
            Text("나를 표현하는 사진을 보여주세요", style = SisoTypoTokens.Title2)
            Spacer(modifier = Modifier.size(size = 8.dp))
            Text(
                "최소 1장 이상 선택해주세요\n정보는 나중에 수정할 수 있어요",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale60
            )

            Spacer(modifier = Modifier.size(size = 44.dp))
            // 사진 홀더
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_photo_holder,
                contentDescription = "Photo holder"
            )
            // 불러온 비트맵 보여주는 리스트
            if(capturedImages.isEmpty()) Spacer(Modifier.size(140.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(capturedImages) { bitmap ->
                    AsyncImage(
                        model = bitmap.asImageBitmap(), contentDescription = "", modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            CommonActiveButton(
                text = "사진 추가하기",
                onClick = {
                    if (capturedImages.size < 5) {
                        viewModel.showPhotoUploadBottomSheet()
                    } else {
                        // 5장 초과 시 처리 로직
                    }
                }
            )
            Spacer(modifier = Modifier.size(size = 72.dp))
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.hidePhotoUploadBottomSheet()
                },
                sheetState = sheetState,
                dragHandle = null,
                containerColor = Color.White
            ) {
                PhotoUploadBottomSheet(
                    // 시트 닫기
                    onDismissRequest = {
                        viewModel.hidePhotoUploadBottomSheet()
                    },
                    // 카메라 권한 실행
                    onTakePhotoClick = {
                        viewModel.hidePhotoUploadBottomSheet()
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onPickFromGalleryClick = {
                        viewModel.hidePhotoUploadBottomSheet()
                        galleryLauncher.launch("image/*")
                    }
                )
            }
        }


    // 권한을 받아 상태를 변경한다면 카메라 프리뷰를 띄운다.
    if (isCameraVisible) {
        CameraPreview(
            onImageCaptured = { bitmap ->
                if (capturedImages.size < 5) {
                    capturedImages.add(bitmap)
                }
                isCameraVisible = false
            }
        )
    }
}

// --- 프리뷰 영역 ---

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ThirdLoginInfoScreenPreview() {
    SisoTheme {
        // 프리뷰를 위한 가상의 ViewModel
        val viewModel = remember { ThirdLoginInfoScreenViewModel() }
        ThirdLoginInfoScreen(viewModel = viewModel)
    }
}

// 💡 PhotoUploadBottomSheet Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PhotoUploadBottomSheetPreview() {
    SisoTheme {
        PhotoUploadBottomSheet(
            onDismissRequest = {},
            onTakePhotoClick = {},
            onPickFromGalleryClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoUploadBottomSheet(
    onDismissRequest: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPickFromGalleryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        // 상단 헤더 (제목과 닫기 버튼)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "사진 업로드 도움말",
                style = SisoTypoTokens.Title2
            )
            IconButton(onClick = onDismissRequest) {
                Icon(
                    painter = painterResource(id = com.likelion.ui.R.drawable.ic_close_24px),
                    contentDescription = "Close"
                )
            }
        }

        // 도움말 텍스트
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "꼭 얼굴이 아니더라도 내가 관심있는 분야의\n사진을 올려줘도 좋아요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.GrayScale60
        )
        Text(
            text = "예시) 반려동물, 꽃, 운동하는 사진, 등산 등",
            style = SisoTypoTokens.Body4,
            color = Color.Gray
        )

        // 예시 이미지들
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_dog,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_flower,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = com.likelion.ui.R.drawable.img_baseball,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
        // 하단 버튼
        CommonActiveButton("카메라로 사진찍기", onClick = onTakePhotoClick)
        Spacer(modifier = Modifier.height(8.dp))
        CommonActiveButton("앨범에서 가져오기", onClick = onPickFromGalleryClick)
        Spacer(modifier = Modifier.height(16.dp)) // 하단 패딩
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

// ... (나머지 Preview 및 PhotoUploadBottomSheet 코드는 동일)