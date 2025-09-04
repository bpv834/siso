package com.likelion.data.home.repository

import com.likelion.domain.home.repository.HomePageRepository
import com.likelion.local.datastore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(
    private val data: DataStoreDataSource
) : HomePageRepository {
    override suspend fun getDialogStatus(): Flow<Boolean> {
        return data.getDialogStatus()
    }

    override suspend fun changeDialogStatus(isDialog: Boolean) {
        data.changeDialogStatus(isDialog)
    }

}