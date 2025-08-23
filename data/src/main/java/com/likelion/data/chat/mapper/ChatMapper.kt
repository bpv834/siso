package com.likelion.data.chat.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.likelion.data.chat.model.CallHistoryEntity
import com.likelion.data.chat.model.ChatHistoryEntity
import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun CallHistoryEntity.toDomain(): CallHistory {
    val formattedTime = callTime.toFormattedTime()
    return CallHistory(
        id = 0,
        nickName = nickName,
        profileImage = profileImage,
        callTime = formattedTime,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun ChatHistoryEntity.toDomain(): ChatHistory {
    val formattedTime = callTime.toFormattedTime()
    return ChatHistory(
        id = 0,
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
