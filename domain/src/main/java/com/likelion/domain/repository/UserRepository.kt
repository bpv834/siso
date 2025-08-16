package com.likelion.domain.repository

import com.likelion.domain.model.UsersModel

interface UsersRepository {
    suspend fun getAllUsers(): List<UsersModel>
}