package com.likelion.remote.model.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BasicTokenResponseDto(
    val refreshToken: String,
    val registrationStatus: RegistrationStatus,
    val hasProfile: Boolean,
)

enum class RegistrationStatus { REGISTER, LOGIN }