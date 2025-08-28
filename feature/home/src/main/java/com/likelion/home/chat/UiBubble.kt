package com.likelion.home.chat

import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat

sealed class UiBubble {
    data class Mine(val data: MyChat) : UiBubble()
    data class Partner(val data: PartnerChat) : UiBubble()
}