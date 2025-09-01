package com.likelion.remote.model.request

import com.google.gson.annotations.SerializedName

data class RejectCallRequest(
    @SerializedName("id") val id: Long,
    @SerializedName("channelName") val channelName: String,
    @SerializedName("token") val token: String,
    @SerializedName("callerId") val callerId: Long,
    @SerializedName("receiverId") val receiverId: Long,
)