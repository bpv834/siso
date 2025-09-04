package com.likelion.remote.model.response

data class UserProfileResponse(
    val nickname: String,
    val age: Int,
    val location: String?,
    val interests: List<String>?,
    val profileImageUrl: String? // null이 올 수 있으므로 nullable로 지정
)