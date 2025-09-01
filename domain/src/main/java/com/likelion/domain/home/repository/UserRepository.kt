package com.likelion.domain.home.repository

import com.likelion.domain.notification.model.UserModel

interface UserRepository {
    suspend fun getMatchingUser() : Result<List<UserModel>>
}