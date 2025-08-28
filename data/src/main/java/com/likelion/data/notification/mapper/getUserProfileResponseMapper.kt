package com.likelion.data.notification.mapper

import com.likelion.domain.notification.model.UserModel
import com.likelion.remote.model.response.UserProfileResponseDto
import timber.log.Timber

// TODO 서버랑 상의
// UserSignUpProfile을 UserProfileRequest로 변환하는 확장 함수
fun UserProfileResponseDto.toUserModel(): UserModel {
    val result = UserModel(
        nickname = this.nickname,
        profileImageUrl = "",
        id = 0L
    )
    Timber.d("result = $result")
    return result
}
