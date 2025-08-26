package com.likelion.domain.login.model

data class UserSignUpProfile(
    var nickname: String = "",
    var age: Int = 0,
    var gender: String = "",
    var preferenceSex: String ="상관없음",
    var photoPaths: List<String> = emptyList(), // 사진 파일 경로 목록
    var introduce: String = "",
    var voicePath: String = "" // 녹음 파일 경로
)