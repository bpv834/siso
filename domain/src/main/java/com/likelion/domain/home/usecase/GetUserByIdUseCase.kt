package com.likelion.domain.home.usecase

import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.repository.UsersRepository

class GetUserByIdUseCase(
    private val usersRepository: UsersRepository
) {
    // Usecase는 Repository가 반환하는 UsersModel 리스트를 받습니다.
    suspend fun execute(id: Long): UsersModel {
        return usersRepository.getUserById(id=id)
    }
}