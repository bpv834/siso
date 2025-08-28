package com.likelion.domain.chat.model

/**
 * @param partnerImg 상대방 프사
 * @param partnerMsg 상대방 채팅
 * @param partnerTime 상대방 채팅 시간
 * @param showTime 마지막 채팅 분기
 * */
data class PartnerChat(
    val partnerImg: String,
    val partnerMsg: String,
    val partnerTime: String,
    val showTime: Boolean = false
)
