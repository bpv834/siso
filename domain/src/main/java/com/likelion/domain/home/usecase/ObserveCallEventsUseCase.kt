package com.likelion.domain.home.usecase

import com.likelion.domain.home.model.AgoraEvent
import com.likelion.domain.home.repository.CallRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ObserveCallEventsUseCase @Inject constructor(
    // di 에서 @binds 로 인터페이스 구현체 연결해서 인터페이스를 받으면 구현체를 사용함
    private val callRepository: CallRepository
) {
    fun execute(): SharedFlow<AgoraEvent> {
        return callRepository.agoraEvents
    }
}