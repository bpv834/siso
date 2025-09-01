package com.likelion.domain.mypage.repository

import com.likelion.domain.model.ImagesModel
import com.likelion.domain.mypage.model.UserEditImageModel

interface UserImageRepository {
    fun getUserImages(userId:Long) : List<UserEditImageModel>
}
