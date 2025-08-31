package com.likelion.login.login_input_photo

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow

interface ThirdLoginInfoScreenViewModelType {
    val showBottomSheet: StateFlow<Boolean>
    val capturedImages: StateFlow<List<Bitmap>>
    fun showPhotoUploadBottomSheet()
    fun hidePhotoUploadBottomSheet()
    fun uploadImagesToServer()
    fun addImageToCaptures(newImage: Bitmap)
    fun deleteBitMap(deleteMitMap : Bitmap)

    // 테스트용 더미 비트맵 리스트를 생성하는 메서드 추가
    fun createMockBitmapList(context: Context): List<Bitmap>
    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): String?
    fun finalizeImagesForSignUp(context : Context)
}