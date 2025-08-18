package com.likelion.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataSource {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }

    override suspend fun getToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[PreferencesKey.ACCESS_TOKEN]
        }
    }


    override suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[PreferencesKey.ACCESS_TOKEN] = token
        }
    }
}