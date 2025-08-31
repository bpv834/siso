package com.likelion.domain.login.repository

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.User
import kotlinx.coroutines.flow.Flow

// 로컬 토큰
interface TokenRepository {
    suspend fun getLocalToken(): Flow<BasicToken?>
    suspend fun clearLocalToken()
    suspend fun saveRefreshToken(token: BasicToken)
    suspend fun getTokenAll(): Flow<User?>
    suspend fun saveTokenAll(user: User)
    suspend fun getFcmToken() : Flow<String?>
    suspend fun saveFcmToken(token : String)
}