package com.likelion.domain.home.repository

import com.likelion.domain.home.model.UsersModel

interface UsersRepository2 {
    suspend fun getAllUsers(): List<UsersModel>
    suspend fun getUserById(id: Long): UsersModel
}