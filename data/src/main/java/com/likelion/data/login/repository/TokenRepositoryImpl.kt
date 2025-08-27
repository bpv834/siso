package com.likelion.data.login.repository

import com.likelion.data.login.mapper.toDomain
import com.likelion.data.login.mapper.toRemote
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.User
import com.likelion.domain.login.repository.TokenRepository
import com.likelion.local.datastore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val data: DataStoreDataSource
) : TokenRepository {

    // DataStore에서 저장된 키를 가져옴
    override suspend fun getLocalToken(): Flow<BasicToken?> {
        return data.getToken().map { it?.toDomain() }
    }

    override suspend fun clearLocalToken() {
        data.clearToken()
    }

    override suspend fun saveRefreshToken(token: BasicToken) {
        data.saveToken(token.toRemote())
    }

    override suspend fun getTokenAll(): Flow<User?> {
        return data.getTokenAll()
    }

    override suspend fun saveTokenAll(user: User) {
        data.saveTokenAll(user)
    }
}