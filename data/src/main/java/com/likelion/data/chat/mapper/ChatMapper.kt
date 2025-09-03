package com.likelion.data.chat.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.likelion.data.chat.model.CallHistoryEntity
import com.likelion.data.chat.model.ChatHistoryEntity
import com.likelion.data.chat.model.MyChatEntity
import com.likelion.data.chat.model.PartnerChatEntity
import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun CallHistoryEntity.toDomain(): CallHistory {
    val formattedTime = callTime.toFormattedTime()
    return CallHistory(
        callId = this.callId,
        nickName = nickName,
        profileImage = profileImage,
        callTime = formattedTime,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun ChatHistoryEntity.toDomain(): ChatHistory {
    val formattedTime = callTime.toFormattedTime()
    return ChatHistory(
        chatRoomId = this.chatRoomId,
        nickName = nickName,
        profileImage = profileImage,
        callTime = formattedTime,
        currentMsg = currentMsg,
        isNew = isView,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormattedTime(
    pattern: String = "HH:mm",
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDateTime()
        .format(DateTimeFormatter.ofPattern(pattern))
}

@RequiresApi(Build.VERSION_CODES.O)
fun MyChatEntity.toDomain(): MyChat {
    return MyChat(
        chatRoomId = this.chatRoomId,
        msg = this.msg,
        time = this.time.toFormattedTime(),
        showTime = this.showTime
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun PartnerChatEntity.toDomain(): PartnerChat {
    return PartnerChat(
        partnerImg = this.partnerImg,
        partnerMsg = this.partnerMsg,
        partnerTime = this.partnerTime.toFormattedTime(),
        showTime = this.showTime
    )
}
