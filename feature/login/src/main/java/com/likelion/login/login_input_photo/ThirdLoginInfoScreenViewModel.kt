package com.likelion.login.login_input_photo

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.SaveTemporaryUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ThirdLoginInfoScreenViewModel @Inject constructor(
    // usecase자리
    val getTemporaryUserProfileUseCase: GetTemporaryUserProfileUseCase,
    val saveTemporaryUserProfileUseCase: SaveTemporaryUserProfileUseCase,
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

    // 사진 삭제 메서드
    override fun deleteBitMap(delete: Bitmap) {
        val mutableList = _capturedImages.value.toMutableList()
        mutableList.remove(delete)
        _capturedImages.value = mutableList
    }

    override fun createMockBitmapList(context: Context): List<coil3.Bitmap> {
        TODO("Not yet implemented")
    }

    override fun saveBitmapToInternalStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): String? {
        // 앱의 내부 파일 저장소 경로를 가져옵니다.
        val directory = context.filesDir
        val file = File(directory, fileName)

        return try {
            // 파일에 데이터를 쓸 수 있는 스트림을 엽니다.
            val fos = FileOutputStream(file)
            // Bitmap을 JPEG 형식으로 압축하여 파일에 씁니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()
            // 저장된 파일의 절대 경로를 반환합니다.
            file.absolutePath
        } catch (e: IOException) {
            // 오류가 발생하면 로그를 남기고 null을 반환합니다.
            e.printStackTrace()
            null
        }
    }
    // 비트맵을 내부저장소에 저장하고 경로를 메모리 레포에 저장하는 메서드
    override fun finalizeImagesForSignUp(context : Context) {
        viewModelScope.launch {

            val userImagePaths = mutableListOf<String>()

            // Create a timestamp string.
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

            _capturedImages.value.forEachIndexed { index, bitmap ->

                val fileName = "profile_image_${timeStamp}_$index.jpg"
                // 비트맵을 저장
                val path = saveBitmapToInternalStorage(context, bitmap, fileName)
                if (path != null) {
                    userImagePaths.add(path)
                }
            }

            _capturedImages.value = emptyList()

            val tempoUser = getTemporaryUserProfileUseCase.execute()
            tempoUser.photoPaths = userImagePaths.toList()

            saveTemporaryUserProfileUseCase.execute(tempoUser)

        }
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