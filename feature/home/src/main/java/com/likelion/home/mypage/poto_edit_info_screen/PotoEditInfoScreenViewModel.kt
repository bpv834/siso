package com.likelion.home.mypage.poto_edit_info_screen

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.model.ImagesModel
import com.likelion.domain.mypage.model.UserEditImageModel
import com.likelion.domain.mypage.usecase.GetUserImagesUseCase
import com.likelion.ui.component.photo_layout.EditableImage
import com.likelion.home.mypage.getBitmapFromUrl
import com.likelion.home.mypage.main_edit_info_screen.EditUiState
import com.likelion.ui.component.photo_layout.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PotoEditInfoScreenViewModel @Inject constructor(
    // usecase자리

) : ViewModel(), PotoEditInfoScreenViewModelType {
    // 바텀 시트의 표시 여부를 관리하는 StateFlow
    private val _showBottomSheet = MutableStateFlow(false)
    override val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    // 사진 리스트의 상태를 관리하는 StateFlow
    private val _capturedImages = MutableStateFlow<List<EditableImage>>(emptyList())
    override val capturedImages: StateFlow<List<EditableImage>> = _capturedImages.asStateFlow()


    fun fetch(imageItems: List<EditableImage>) {
        viewModelScope.launch(Dispatchers.IO) {
            _capturedImages.update {
                // 사진 받는 선행 함수
                imageItems
            }
        }
    }

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
                if (item.edited?.id == delete.id) {
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
        viewModelScope.launch {
            _capturedImages.value.forEach { bitmap ->
                // 구현해야함
/*                val byteArray = convertBitmapToByteArray(bitmap) // 비트맵을 바이트 어레이로 변환하는 함수
                photoRepository.uploadPhoto(byteArray) // Repository를 통해 서버에 전송*/
            }
        }
    }
}