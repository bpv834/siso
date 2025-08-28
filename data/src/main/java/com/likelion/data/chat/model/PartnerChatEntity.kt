package com.likelion.data.chat.model

data class PartnerChatEntity(
    val partnerImg: String,
    val partnerMsg: String,
    val partnerTime: Long,
    val showTime: Boolean = false
)
