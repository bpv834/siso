package com.likelion.home.mypage.poto_edit_info_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FakePotoEditInfoScreenViewModel(
    // usecase
    private val context: Context // Context를 생성자에서 주입
) : PotoEditInfoScreenViewModelType {
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

    // 사진 삭제 메서드
    override fun deleteBitMap(delete: Bitmap) {
        val mutableList = _capturedImages.value.toMutableList()
        mutableList.remove(delete)
        _capturedImages.value = mutableList
    }

    override fun createMockBitmapList(context: Context): List<coil3.Bitmap> {
        TODO("Not yet implemented")
    }



    // 서버에 사진을 업로드하는 메서드 (비동기 처리)
    override fun uploadImagesToServer() {

    }
}