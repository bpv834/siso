package com.likelion.domain.home.usecase

import com.likelion.domain.home.repository.CallRepository
import javax.inject.Inject

class CallUseCase @Inject constructor(
private val callRepository : CallRepository
) {
    fun execute() : String{
        return callRepository.call()
    }
}