package com.likelion.domain.image.repository

import com.likelion.domain.image.model.ImageModel

interface ImageRepository {
    fun getImage(userId : String) : Result<ImageModel>
    fun upLoadImage(imgPath : String) : Result<Unit>
}