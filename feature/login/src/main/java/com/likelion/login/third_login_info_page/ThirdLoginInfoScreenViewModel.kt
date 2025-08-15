package com.likelion.login.third_login_info_page

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirdLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
) : ViewModel(), ThirdLoginInfoScreenViewModelType {
    // 바텀 시트의 표시 여부를 관리하는 StateFlow
    private val _showBottomSheet = MutableStateFlow(false)
    override val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    // 사진 리스트의 상태를 관리하는 StateFlow
    private val _capturedImages = MutableStateFlow<List<Bitmap>>(emptyList())
    override val capturedImages: StateFlow<List<Bitmap>> = _capturedImages.asStateFlow()


    // 바텀 시트 여는 메서드
    override fun showPhotoUploadBottomSheet() {
        _showBottomSheet.value = true
    }

    // 바텀 시트 닫는 메서드
    override fun hidePhotoUploadBottomSheet() {
        _showBottomSheet.value = false
    }

    override fun addImageFromAlbum(newImage: Bitmap) {
        _capturedImages.value = _capturedImages.value + newImage
    }

    override fun createMockBitmapList(context: Context): List<coil3.Bitmap> {
        TODO("Not yet implemented")
    }



    // 서버에 사진을 업로드하는 메서드 (비동기 처리)
    override fun uploadImagesToServer() {
        viewModelScope.launch {
            _capturedImages.value.forEach { bitmap ->
                // 구현해야함
           /*     val byteArray = convertBitmapToByteArray(bitmap) // 비트맵을 바이트 어레이로 변환하는 함수
                photoRepository.uploadPhoto(byteArray) // Repository를 통해 서버에 전송*/
            }
        }
    }
}