package com.likelion.domain.home.usecase

import com.likelion.domain.home.repository.HomePageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDialogStatusUseCase @Inject constructor(
    private val repository: HomePageRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.getDialogStatus()
    }
}