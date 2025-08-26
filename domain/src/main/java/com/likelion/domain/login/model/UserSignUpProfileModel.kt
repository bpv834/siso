package com.likelion.domain.login.model

data class UserSignUpProfile(
    val nickname: String = "",
    val age: Int = 0,
    val gender: String = "",
    val preferenceSex: String ="상관없음",
    val photoPaths: List<String> = emptyList(), // 사진 파일 경로 목록
    val introduce: String = "",
    val voicePath: String = "" // 녹음 파일 경로
)