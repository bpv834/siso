package com.likelion.data.login.repository

import com.likelion.domain.login.repository.AuthTokenRepository
import com.likelion.local.datastore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val data: DataStoreDataSource
) : AuthTokenRepository {
    override suspend fun saveAuthToken(token: String) {
        data.saveToken(token = token)
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return data.getToken()
    }

}