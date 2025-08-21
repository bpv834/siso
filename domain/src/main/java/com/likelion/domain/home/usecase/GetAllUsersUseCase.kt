package com.likelion.domain.home.usecase

import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.home.repository.UsersRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val usersRepository : UsersRepository
) {
    // Usecase는 Repository가 반환하는 UsersModel 리스트를 받습니다.
    suspend fun execute(): List<UsersModel> {
        return usersRepository.getAllUsers()
    }
}