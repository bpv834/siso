package com.likelion.domain.home.repository

import kotlinx.coroutines.flow.Flow

interface HomePageRepository {
    suspend fun getDialogStatus(): Flow<Boolean>
    suspend fun changeDialogStatus(isDialog: Boolean)
}