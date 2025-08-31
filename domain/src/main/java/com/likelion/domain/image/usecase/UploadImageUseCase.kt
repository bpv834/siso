package com.likelion.domain.image.usecase

import com.likelion.domain.image.repository.ImageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    val repository: ImageRepository
) {
    suspend fun execute(eccessToken : String, imgList:List<String>) {
        repository.upLoadImage(eccessToken = eccessToken, imgPathList = imgList)
    }
}