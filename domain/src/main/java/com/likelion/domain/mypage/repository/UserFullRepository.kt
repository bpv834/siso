package com.likelion.domain.mypage.repository

import com.likelion.domain.mypage.model.UsersFullModel

interface UserFullRepository {
    suspend fun getUserById(accessToken: String): UsersFullModel
}