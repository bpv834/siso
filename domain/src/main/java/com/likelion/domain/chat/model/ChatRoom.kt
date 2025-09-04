package com.likelion.domain.chat.model

/**
 * 채팅방 전체 내역 가져오기
 * 프로필 사진
 * 닉네임
 * 마지막 채팅기록
 * 시간
 * 읽음 처리
 * */
data class ChatRoom(
    val profileImage: String,
    val nickname: String,
    val chatTime: String,
    val hasUnreadMessages: Boolean,
    val memberCount: Int,
    val lastChat: String,
)