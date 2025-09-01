package com.likelion.login.login_input_photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class FakeThirdLoginScreenViewModel(
    // usecase
    private val context: Context // Context를 생성자에서 주입
) : ThirdLoginInfoScreenViewModelType {

    // 바텀 시트의 표시 여부를 관리하는 StateFlow
    private val _showBottomSheet = MutableStateFlow(false)
    override val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    // 사진 리스트의 상태를 관리하는 StateFlow
    private val _capturedImages = MutableStateFlow<List<Bitmap>>(emptyList())
    override val capturedImages: StateFlow<List<Bitmap>> = _capturedImages.asStateFlow()

    init {
        // ViewModel이 생성될 때 더미 비트맵 리스트를 초기화
        _capturedImages.value = createMockBitmapList(context)
    }
    // 바텀 시트 여는 메서드
    override fun showPhotoUploadBottomSheet() {
        _showBottomSheet.value = true
    }

    // 바텀 시트 닫는 메서드
    override fun hidePhotoUploadBottomSheet() {
        _showBottomSheet.value = false
    }
    // 앨범에서 비트맵 리스트에 담는 메서드
    override fun addImageToCaptures(newImage: Bitmap) {
        _capturedImages.value = _capturedImages.value + newImage
    }
    // 사진 삭제 메서드
    override fun deleteBitMap(delete: Bitmap) {
        val mutableList = _capturedImages.value.toMutableList()
        mutableList.remove(delete)
        _capturedImages.value = mutableList
    }


    override fun createMockBitmapList(context: Context): List<Bitmap> {
        return listOf(
            createDummyBitmap(100, 100, Color.RED), // 첫 번째 인자에 색상 추가
            createDummyBitmap(100, 100, Color.GREEN),
            // createDummyBitmap(100, 100, Color.BLUE)
        )
    }

    override fun saveBitmapToInternalStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): String? {
        TODO("Not yet implemented")
    }

    override fun finalizeImagesForSignUp(context: Context) {
        TODO("Not yet implemented")
    }


    private fun createDummyBitmap(width: Int, height: Int, color: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(color)
        return bitmap
    }

    override fun uploadImagesToServer() {
    }

}