package com.likelion.domain.notification.usecase

import com.likelion.domain.notification.model.UserModel
import com.likelion.domain.notification.repository.FcmRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: FcmRepository
) {
    suspend operator fun invoke(id: Long): UserModel {
        return repository.getUserById(id)
    }
}