package com.likelion.domain.home.usecase

import com.likelion.domain.home.repository.HomePageRepository
import javax.inject.Inject

class ChangeDialogStatusUseCase @Inject constructor(
    private val repository: HomePageRepository
) {
    suspend operator fun invoke(isDialog: Boolean) {
        return repository.changeDialogStatus(isDialog)
    }
}