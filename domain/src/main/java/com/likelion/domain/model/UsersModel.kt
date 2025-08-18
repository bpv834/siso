package com.likelion.domain.model



data class UsersModel(
    val id: Long,
    val isOnline: Boolean, // 온라인 여부
    val userImages: List<String>, // 유저 이미지 목록
    val location: String, // 사는 위치
    val nickname: String, // 닉네임
    val age: Int, // 나이
    val voiceUrl: String, // 음성 녹음본 URL
    val interests: List<String>, // 관심사 목록
    val introduce: String // 자기소개
)