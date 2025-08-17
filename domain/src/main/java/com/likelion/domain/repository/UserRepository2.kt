package com.likelion.domain.repository

import com.likelion.domain.model.UsersModel

interface UsersRepository2 {
    suspend fun getAllUsers(): List<UsersModel>
    suspend fun getUserById(id: Long): UsersModel
}