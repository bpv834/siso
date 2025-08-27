package com.likelion.home.mypage.poto_edit_info_screen

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow

interface PotoEditInfoScreenViewModelType {
    val showBottomSheet: StateFlow<Boolean>
    val capturedImages: StateFlow<List<Bitmap>>
    fun showPhotoUploadBottomSheet()
    fun hidePhotoUploadBottomSheet()
    fun uploadImagesToServer()
    fun addImageFromAlbum(newImage: Bitmap)
    fun deleteBitMap(deleteMitMap : Bitmap)
    fun addAllImageFromAlbum(list: List<Bitmap>)

    // 테스트용 더미 비트맵 리스트를 생성하는 메서드 추가
    fun createMockBitmapList(context: Context): List<Bitmap>
}