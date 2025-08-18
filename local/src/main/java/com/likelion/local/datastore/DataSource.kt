package com.likelion.local.datastore

import kotlinx.coroutines.flow.Flow

// DataStore에서 가져올 인터페이스
interface DataSource {
    suspend fun getToken(): Flow<String?>
    suspend fun saveToken(token: String)
}