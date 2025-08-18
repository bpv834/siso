package com.likelion.domain.login.repository

import kotlinx.coroutines.flow.Flow

interface AuthTokenRepository {
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): Flow<String?>
}