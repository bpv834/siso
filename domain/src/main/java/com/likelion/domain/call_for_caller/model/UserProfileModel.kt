package com.likelion.domain.call_for_caller.model

data class UserProfileModel(
    val nickname: String,
    val age: Int,
    val location: String,
    val interests: List<String> ,
    val profileImageUrl: String
)
