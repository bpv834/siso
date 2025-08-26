package com.likelion.data.home.mapper

import com.likelion.domain.call_for_caller.model.CallInfoModel
import com.likelion.remote.model.response.CallInfoDto


fun CallInfoDto.toDomainModel(): CallInfoModel {
    return CallInfoModel(
        channelName = this.channelName,
        token = this.token
    )
}