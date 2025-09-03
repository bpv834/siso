package com.likelion.data.call_for_caller.mapper

import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.remote.model.response.CallInfoDto


fun CallInfoDto.toDomainModel(): CallModel {
    return CallModel(
        id = this.id,
        channelName = this.channelName,
        receiverId = this.receiverId,
        callerId = this.callerId,
        agoraToken = this.token,
    )
}