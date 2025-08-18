package com.likelion.domain.usecase

import com.likelion.domain.model.UsersModel
import com.likelion.domain.repository.UsersRepository2

class GetAllUsersUseCase(
    private val usersRepository2: UsersRepository2
) {
    // Usecase는 Repository가 반환하는 UsersModel 리스트를 받습니다.
    suspend fun execute(): List<UsersModel> {
        return usersRepository2.getAllUsers()
    }
}