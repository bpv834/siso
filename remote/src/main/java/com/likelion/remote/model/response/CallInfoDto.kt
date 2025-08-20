package com.likelion.remote.model.response

// 서버로부터 받을 통화 정보 데이터 클래스
data class CallInfoDto(
    val channelName: String,
    val token: String,
    val uid: Int = 0 // 서버에서 사용자 ID를 할당할 경우를 대비
)