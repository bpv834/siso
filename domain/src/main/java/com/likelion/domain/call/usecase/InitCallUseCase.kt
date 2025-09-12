package com.likelion.domain.call.usecase

import com.likelion.domain.call.repository.CallRepository
import javax.inject.Inject

class InitCallUseCase @Inject constructor(
    private val callRepository: CallRepository
) {
    fun execute() {
        return callRepository.initialize()
    }
}