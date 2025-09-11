package com.likelion.domain.call.model

data class UsersModel(
    val id: Long,
    val userImages: String, // 유저 이미지 목록
    val location: String, // 사는 위치
    val nickname: String, // 닉네임
    val age: Int, // 나이
    val interests: List<String>, // 관심사 목록
)