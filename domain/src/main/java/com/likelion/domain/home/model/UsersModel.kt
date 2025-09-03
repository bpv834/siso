package com.likelion.domain.home.model

import com.likelion.domain.enums.PresentStatus

data class UsersModel(
    val id: Long,
    val imageList: List<String>, // 유저 이미지 목록
    val location: String, // 사는 위치
    val nickname: String, // 닉네임
    val age: Int, // 나이
    val voiceUrl: String, // 음성 녹음본 URL
    val interests: List<String>, // 관심사 목록
    val introduce: String, // 자기소개
    val presentStatus: PresentStatus,
)