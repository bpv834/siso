package com.likelion.data.mypage.repository

import com.likelion.domain.mypage.model.UserEditImageModel
import com.likelion.domain.mypage.repository.UserImageRepository

class UserImageRepositoryImpl(

) : UserImageRepository {
    override fun getUserImages(userId: Long): List<UserEditImageModel> {
        val images =listOf(
            UserEditImageModel(
                userId = 13L,
                path = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                serverImageName = "",
                originalName = "",
            ),
            UserEditImageModel(
                userId = 13L,
                path = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                serverImageName = "",
                originalName = "",
            ),
            UserEditImageModel(
                userId = 13L,
                path = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                serverImageName = "",
                originalName = "",
            ),
            UserEditImageModel(
                userId = 13L,
                path = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                serverImageName = "",
                originalName = "",
            ),
        )
        return images
    }
}