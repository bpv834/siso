package com.likelion.domain.call.model

data class UserProfileModel(
    val nickname: String,
    val age: Int,
    val location: String,
    val interests: List<String> ,
    val profileImageUrl: String
)
