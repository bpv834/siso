package com.likelion.domain.mypage.usecase

import com.likelion.domain.mypage.model.Location
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.repository.UserFullRepository
import javax.inject.Inject

class UsersFullUseCase(
    private val usersFullRepository: UserFullRepository
) {
    suspend operator fun invoke(id: Long): UsersFullModel =
        usersFullRepository.getUserById(id)
}