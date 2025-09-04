package com.likelion.local.datastore

import com.likelion.domain.login.model.User
import com.likelion.local.model.BasicTokenEntity
import kotlinx.coroutines.flow.Flow

// DataStore에서 가져올 인터페이스
interface DataSource {
    suspend fun getToken(): Flow<BasicTokenEntity?>
    suspend fun clearToken()
    suspend fun saveToken(token: BasicTokenEntity)
    suspend fun saveTokenAll(user: User)
    suspend fun getTokenAll(): Flow<User?>
    suspend fun saveFcmToken(token: String)
    fun getFcmToken(): Flow<String?>

    // 온보딩 설정
    suspend fun changeOnBoardingSkip(isSkip: Boolean)
    suspend fun getOnBoardingSkip(): Flow<Boolean>

}