package com.likelion.data.home.mapper

import com.likelion.domain.call.model.CallModel
import com.likelion.remote.model.response.CallInfoDto


fun CallInfoDto.toDomainModel(): CallModel {
    return CallModel(
        id = this.id,
        channelName = this.channelName,
        agoraToken = this.token,
        callerId = this.callerId,
        receiverId = this.receiverId
    )
}