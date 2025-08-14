package com.likelion.login.ThirdLoginInfoPage

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow

interface ThirdLoginInfoScreenViewModelType {
    val showBottomSheet: StateFlow<Boolean>
    val capturedImages: StateFlow<List<Bitmap>>
    fun showPhotoUploadBottomSheet()
    fun hidePhotoUploadBottomSheet()
    fun uploadImagesToServer()
    fun addImageFromAlbum(newImage: Bitmap)

    // 테스트용 더미 비트맵 리스트를 생성하는 메서드 추가
    fun createMockBitmapList(context: Context): List<Bitmap>
}