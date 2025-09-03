package com.example.notification

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object FcmEventBus {
    private val _events = MutableSharedFlow<FcmEvent>()
    val events = _events.asSharedFlow()

    fun send(event: FcmEvent) {
        // Dispatchers.Main : UI 이벤트
        // Dispachers.IO : 파일/DB/네트워크 I/O처럼 블로킹 작업에 적합
        // Default : CPU 연산이 많을 때, 예를 들어 복잡한 데이터 변환, 계산
        CoroutineScope(Dispatchers.Main).launch {
            _events.emit(event)
        }
    }
}