package com.likelion.domain.image.repository

import com.likelion.domain.image.model.ImageModel

interface ImageRepository {
    suspend fun getImagesByUserId(userId : String,refreshToken: String) : Result<List<ImageModel>>
    suspend fun upLoadImage(imgPathList: List<String>, eccessToken: String): Result<Unit>
    // 단일 String → 리스트 지원으로 변경
    suspend fun getPresignedImgs(imgIds: List<Long>, accessToken: String): List<String>
}