package com.likelion.domain.image.usecase

import com.likelion.domain.image.repository.ImageRepository
import javax.inject.Inject

class GetPresignedImgsUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend fun execute(imgIds: List<Long>, accessToken: String): List<String> {
        return repository.getPresignedImgs(imgIds, accessToken)
    }
}