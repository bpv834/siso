package com.likelion.data.call.mapper


import com.likelion.domain.call.model.CallResponseModel
import com.likelion.remote.model.response.CallResponseDto

fun CallResponseDto.toDomain(): CallResponseModel {
    return CallResponseModel(
        accepted = this.accepted,
        token = this.token,
        channelName = this.channelName,
        callerId = this.callerId,
        receiverId = this.receiverId,
        callStatus = this.callStatus,
        duration = this.duration,
        callerProfile = this.callerProfile.toDomain(),
        receiverProfile = this.receiverProfile.toDomain()
    )
}
