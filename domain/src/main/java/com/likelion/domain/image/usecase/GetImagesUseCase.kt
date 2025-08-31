package com.likelion.domain.image.usecase

import com.likelion.domain.image.repository.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    val repository: ImageRepository
) {
    suspend fun execute(userId: String, refreshToken : String) {
        repository.getImagesByUserId(userId = "", refreshToken = "")
    }
}