package com.likelion.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponseDto(
    val accessToken: String,
    val user: UserInfoResponse,
    val token: ApiToken
)


@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    val id: Long,
    val provider: String,
    val email: String,
    val phoneNumber: String,
    val deleted: Boolean,
    val block: Boolean
)

@JsonClass(generateAdapter = true)
data class ApiToken(
    val refreshToken: String,
    @Json(name = "registrationStatus") val registrationStatus: String
)