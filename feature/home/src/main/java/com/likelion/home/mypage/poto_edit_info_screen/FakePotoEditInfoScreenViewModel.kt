package com.likelion.home.mypage.poto_edit_info_screen

import android.content.Context
import android.graphics.Bitmap
import com.likelion.ui.component.photo_layout.EditableImage
import com.likelion.ui.component.photo_layout.ImageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class FakePotoEditInfoScreenViewModel(
    // usecase
    private val context: Context // Context를 생성자에서 주입
) : PotoEditInfoScreenViewModelType {
    // 바텀 시트의 표시 여부를 관리하는 StateFlow
    private val _showBottomSheet = MutableStateFlow(false)
    override val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    // 사진 리스트의 상태를 관리하는 StateFlow
    private val _capturedImages = MutableStateFlow<List<EditableImage>>(emptyList())
    override val capturedImages: StateFlow<List<EditableImage>> = _capturedImages.asStateFlow()


    // 바텀 시트 여는 메서드
    override fun showPhotoUploadBottomSheet() {
        _showBottomSheet.value = true
    }

    // 바텀 시트 닫는 메서드
    override fun hidePhotoUploadBottomSheet() {
        _showBottomSheet.value = false
    }

    override fun addImageFromAlbum(newImage: Bitmap) {

        val imageItem = EditableImage(null, ImageItem.BitmapImage(newImage))
        _capturedImages.value = _capturedImages.value + imageItem
    }

    // 사진 삭제 메서드
    override fun deleteImageItem(delete: ImageItem) {
        _capturedImages.update {
            capturedImages.value.map { item ->
                if (item.edited == delete) {
                    item.copy(edited = null)
                } else {
                    item
                }
            }
        }
    }

    override fun createMockBitmapList(context: Context): List<coil3.Bitmap> {
        TODO("Not yet implemented")
    }



    // 서버에 사진을 업로드하는 메서드 (비동기 처리)
    override fun uploadImagesToServer() {

    }
}