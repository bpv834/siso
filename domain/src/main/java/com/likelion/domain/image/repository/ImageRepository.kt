package com.likelion.domain.image.repository

import com.likelion.domain.image.model.ImageModel

interface ImageRepository {
    suspend fun getImagesByUserId(userId : String,refreshToken: String) : Result<List<ImageModel>>
    suspend fun upLoadImage(imgPathList: List<String>, eccessToken: String): Result<Unit>
}