package com.likelion.domain.call_for_caller.model

data class CallResponseModel(
    val accepted: Boolean,
    val token: String,
    val channelName: String,
    val callerId: Int,
    val receiverId: Int,
    val callStatus: String,
    val duration: Int,
    val callerProfile: UserProfileModel,
    val receiverProfile: UserProfileModel
)
