package com.likelion.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.User
import com.likelion.domain.login.model.UserStatus
import com.likelion.local.model.BasicTokenEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataSource {

    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val USER_STATUS = stringPreferencesKey("USER_STATUS")
        val USER_JSON = stringPreferencesKey("USER_JSON")

    }

    // refresh / Status 저장

    // access / refresh /status / user 저장

    // 저장해 놓은 토큰 값 확인
    override suspend fun getToken(): Flow<BasicTokenEntity?> {
        return dataStore.data.map { prefs ->
            val refresh = prefs[PreferencesKey.REFRESH_TOKEN]
            val status = prefs[PreferencesKey.USER_STATUS]
            if (status.isNullOrBlank() && refresh.isNullOrBlank()) {
                null
            } else BasicTokenEntity(
                refreshToken = refresh!!,
                status = status!!
            )
        }
    }

    // 키 초기화
    override suspend fun clearToken() {
        dataStore.edit { prefs ->
            prefs.remove(PreferencesKey.ACCESS_TOKEN)
            prefs.remove(PreferencesKey.REFRESH_TOKEN)
            prefs.remove(PreferencesKey.USER_STATUS)
            prefs.remove(PreferencesKey.USER_JSON)
        }
    }

    override suspend fun saveToken(token: BasicTokenEntity) {
        Log.d("saveRefresh","${token}")
        dataStore.edit { prefs ->
            prefs[PreferencesKey.REFRESH_TOKEN] = token.refreshToken
            prefs[PreferencesKey.USER_STATUS] = token.status
        }
    }

    override suspend fun saveTokenAll(user: User) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(User::class.java)
        // 저장
        val userJson = adapter.toJson(user)
        Log.d("saveTokenAll", "userJson = $userJson")
        dataStore.edit { prefs ->
            prefs[PreferencesKey.USER_JSON] = userJson
        }
    }

    override suspend fun getTokenAll(): Flow<User?> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(User::class.java)
        return dataStore.data.map { prefs ->
            val json = prefs[PreferencesKey.USER_JSON]
            if (json.isNullOrBlank()) {
                null
            } else {
                runCatching {
                    adapter.fromJson(json)
                }.getOrNull()
            }
        }
    }


}