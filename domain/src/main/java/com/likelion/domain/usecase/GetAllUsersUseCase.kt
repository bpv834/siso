package com.likelion.domain.usecase

import com.likelion.domain.model.UsersModel
import com.likelion.domain.repository.UsersRepository

class GetAllUsersUseCase( private val usersRepository: UsersRepository) {
    // Usecase는 Repository가 반환하는 UsersModel 리스트를 받습니다.
    suspend operator fun invoke(): List<UsersModel> {
        return usersRepository.getAllUsers()
    }
}