package com.likelion.domain.mypage.usecase

import com.likelion.domain.login.model.User
import com.likelion.domain.model.ImagesModel
import com.likelion.domain.mypage.model.UserEditImageModel
import com.likelion.domain.mypage.repository.UserImageRepository
import javax.inject.Inject

class GetUserImagesUseCase @Inject constructor(
    val getUserImageRepository: UserImageRepository
){
    operator fun invoke(id:Long): List<UserEditImageModel> =
        getUserImageRepository.getUserImages(id)

}