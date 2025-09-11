package com.likelion.domain.call.model

// 도메인 계층에서 사용할 Call 거절 요청 모델
data class CallModel(
    val id : Long,
    val callerId: Long,         // 발신자 ID
    val channelName: String,   // 통화 채널 이름
    val agoraToken: String,        // Agora 토큰
    val receiverId: Long,       // 수신자 ID
)