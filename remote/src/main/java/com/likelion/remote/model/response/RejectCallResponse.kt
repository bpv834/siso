package com.likelion.remote.model.response

import com.google.gson.annotations.SerializedName

data class RejectCallResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null
)