package com.likelion.login.login_input_photo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.photo_layout.PhotoDisplaySection
import com.likelion.ui.component.photo_layout.PhotoUploader
import com.likelion.ui.component.text_button.CommonTextButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalMaterial3Api
fun ThirdLoginInfoScreen(
    viewModel: ThirdLoginInfoScreenViewModelType,
    onNavigateNext: () -> Unit
) {

    val context = LocalContext.current
    // 캡쳐된 이미지 보관 변수
    val capturedImages by viewModel.capturedImages.collectAsStateWithLifecycle()
    // 바텀시트 상태 관리 변수
    val showBottomSheetState by viewModel.showBottomSheet.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(size = 8.dp))
        AsyncImage(
            model = R.drawable.img_circle_bar_login3,
            contentDescription = "Step indicator",
        )
        Spacer(modifier = Modifier.size(size = 27.dp))
        Text(text = "나를 표현하는 사진을 보여주세요", style = SisoTypoTokens.Title2)
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text(
            text = "최소 1장 이상 선택해주세요\n정보는 나중에 수정할 수 있어요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
        )
        Spacer(modifier = Modifier.size(size = 9.dp))
        // 불러온 비트맵 보여주는 리스트
        // 초기 이미지가 없다면 홀더를 보여줌
        PhotoDisplaySection(
            capturedImages = capturedImages,
            // 시트 보이게 하는 로직
            onAddButtonClick = { viewModel.showPhotoUploadBottomSheet() },
            onDelete = { bitmap -> viewModel.deleteBitMap(bitmap) },
        )
        // 건너띄기가 보일땐 24로 사진추가,다음으로 버튼일땐 8로 가깝게
        val spaceSize = if (capturedImages.size != 0) 8.dp else 24.dp
        Spacer(Modifier.size(spaceSize))
        if (capturedImages.isEmpty()) // 이미지가 없을때만 건너뛰기를 노출
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonTextButton(
                    text = "건너뛰기",
                    style = SisoTypoTokens.Button2,
                    color = SisoColorTokens.Gray60,
                    onClick = { onNavigateNext() }
                )
            }
        // 사진이 한개라도 있다면 다음으로 버튼 노출
        if (capturedImages.isNotEmpty())
            CommonActiveButton(
                text = "다음으로", modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp), onClick = {
                    viewModel.finalizeImagesForSignUp(context = context)
                    onNavigateNext()
                })
    }
    // 사진 추가 바텀시트 및 카메라, 앨범 런처
    PhotoUploader(
        onDismissRequest = {
            viewModel.hidePhotoUploadBottomSheet()
        },
        // 갤러리에서 사진 가져와 images에 추가하는 메서드
        onImagePicked = { bitmap ->
            viewModel.addImageToCaptures(bitmap)
        },
        onImageCaptured = { bitmap ->
            viewModel.addImageToCaptures(bitmap)

        },
        showBottomSheet = showBottomSheetState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ThirdLoginInfoScreenPreview() {
    SisoTheme {
        val context = LocalContext.current
        val fakeViewModel = FakeThirdLoginScreenViewModel(context)
        ThirdLoginInfoScreen(fakeViewModel) { }
    }
}