package com.likelion.home.mypage.poto_edit_info_screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.home.mypage.getBitmap
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.button.CommonButtonWithState
import com.likelion.ui.component.camera.CameraPreview
import com.likelion.ui.component.photo_layout.PhotoLayoutWith1Main4Sub
import com.likelion.ui.component.photo_layout.SealedPhotoLayoutWith1Main4Sub
import com.likelion.ui.component.text_button.CommonTextButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalMaterial3Api
fun PotoEditInfoScreen(
    viewModel: PotoEditInfoScreenViewModelType,
    onNavigateNext: () -> Unit
) {
    val showBottomSheet by viewModel.showBottomSheet.collectAsStateWithLifecycle()
    // 💡 바텀 시트 상태 수정:
    // skipPartiallyExpanded = true: 부분 확장 상태를 건너뜁니다.
    // initialValue = SheetValue.Expanded: 바텀 시트가 처음부터 확장된 상태로 열립니다.
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val context = LocalContext.current
    // 카메라 노출 여부 상태 변수
    var isCameraVisible by remember { mutableStateOf(false) }
    // 가져온 비트맵 저장하는 리스트 변수
    val capturedImages by viewModel.capturedImages.collectAsStateWithLifecycle()
    val edit = capturedImages.filter { it.edited != null }.map { it.edited!! }
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
                viewModel.addImageFromAlbum(newImage = bitmap)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(SisoColorTokens.White)
    ) {
        Spacer(modifier = Modifier.size(size = 27.dp))
        Text(text = "나를 표현하는 사진을 보여주세요", style = SisoTypoTokens.Title2)
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text(
            text = "최소 1장 이상 선택해주세요\n정보는 나중에 수정할 수 있어요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        Text(text = "대표사진", style = SisoTypoTokens.SubTitle1, color = SisoColorTokens.Gray55)
        Spacer(modifier = Modifier.size(size = 9.dp))
        // 불러온 비트맵 보여주는 리스트
        SealedPhotoLayoutWith1Main4Sub(
            mainWith = null,
            mainHeight = 206.dp,
            subWith = 76.dp,
            subHeight = 72.dp,
            capturedImages = edit,
            onClickDelete = { imageItem -> viewModel.deleteImageItem(imageItem) }
        )
        Spacer(modifier = Modifier.size(size = 68.dp))
        // derivedStateOf는 다른 상태에서 파생된 값을 안전하고 효율적으로 계산하고 싶을 때 쓰는 도구예요.

        CommonButtonWithState (text = "사진 추가하기 (${edit.size}/5)",
            onClick = {
                // 5장 초과 시 처리 로직
                viewModel.showPhotoUploadBottomSheet()
            },
            isActive = edit.size < 5
        )
        Spacer(Modifier.size(8.dp))
        if (capturedImages.isEmpty()) // 이미지가 없을때만 건너뛰기를 노출
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonTextButton(
                    text = "건너뛰기",
                    style = SisoTypoTokens.Button2,
                    color = SisoColorTokens.Gray50,
                    onClick = {}
                )
            }
        // 사진이 한개라도 있다면 다음으로 버튼 노출
        if (capturedImages.isNotEmpty())
            CommonActiveButton(
                modifier = null,
                text = "다음으로", onClick = {
                onNavigateNext()
            })
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
            PhotoEditUploadBottomSheet(
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
                    viewModel.addImageFromAlbum(newImage = bitmap)
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
fun PotoEditInfoScreenPreview() {
    SisoTheme {
        // 프리뷰를 위한 가상의 ViewModel
        val viewModel = FakePotoEditInfoScreenViewModel(LocalContext.current)
        PotoEditInfoScreen(
            viewModel = viewModel,
            onNavigateNext = {}
        )
    }
}

// 💡 PhotoUploadBottomSheet Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PhotoEditUploadBottomSheetPreview() {
    SisoTheme {
        PhotoEditUploadBottomSheet(
            onDismissRequest = {},
            onTakePhotoClick = {},
            onPickFromGalleryClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoEditUploadBottomSheet(
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
                    painter = painterResource(id = R.drawable.ic_close_24px),
                    contentDescription = "Close"
                )
            }
        }

        // 도움말 텍스트
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "꼭 얼굴이 아니더라도 내가 관심있는 분야의\n사진을 올려줘도 좋아요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
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
                model = R.drawable.img_dog,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = R.drawable.img_flower,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = R.drawable.img_baseball,
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

// ... (나머지 Preview 및 PhotoUploadBottomSheet 코드는 동일)