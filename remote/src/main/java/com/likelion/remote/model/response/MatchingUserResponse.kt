package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName
import com.likelion.remote.model.enums.RemotePresentStatus


data class MatchingUserResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("age") val age: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("interests") val interests: List<String>,
    @SerializedName("introduce") val introduce: String?,
    @SerializedName("imageUrls") val imageUrls: List<String>,
    @SerializedName("voiceSampleUrl") val voiceSampleUrl: String?,
    @SerializedName("presenseStatus") val presenseStatus: RemotePresentStatus
)