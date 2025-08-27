package com.likelion.data.login.mapper

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.User
import com.likelion.domain.login.model.UserInfo
import com.likelion.domain.login.model.UserStatus
import com.likelion.local.model.BasicTokenEntity
import com.likelion.remote.model.response.BasicTokenResponseDto
import com.likelion.remote.model.response.RegistrationStatus
import com.likelion.remote.model.response.UserInfoResponse
import com.likelion.remote.model.response.UserInfoResponseDto


fun BasicTokenResponseDto.toDomain(): BasicToken {
    return BasicToken(
        refreshToken = this.refreshToken,
        userStatus = when (this.registrationStatus) {
            RegistrationStatus.REGISTER -> UserStatus.REGISTER
            RegistrationStatus.LOGIN -> UserStatus.LOGIN
        }
    )
}

fun BasicToken.toRemote(): BasicTokenEntity {
    return BasicTokenEntity(
        refreshToken = this.refreshToken,
        status = this.userStatus.name
    )
}

fun BasicTokenEntity.toDomain(): BasicToken {
    return BasicToken(
        refreshToken = this.refreshToken,
        userStatus = when (this.status) {
            "LOGIN" -> UserStatus.LOGIN
            "REGISTER" -> UserStatus.REGISTER
            else -> UserStatus.NONE
        }
    )
}

fun UserInfoResponse.toDomain(): UserInfo {
    return UserInfo(
        email = this.email,
        id = this.id,
        provider = this.provider,
        phoneNumber = this.phoneNumber,
        deleted = this.deleted,
        block = this.block
    )
}

fun UserInfoResponseDto.toDomain(): User = User(
    accessToken = accessToken,
    refreshToken = token.refreshToken,
    userStatus = token.registrationStatus.toUserStatus(),
    userInfo = UserInfo(
        id = user.id,
        provider = user.provider,
        email = user.email,
        phoneNumber = user.phoneNumber,
        deleted = user.deleted,
        block = user.block
    )
)
private fun String.toUserStatus(): UserStatus = when (this) {
    "LOGIN" -> UserStatus.LOGIN
    "REGISTER" -> UserStatus.REGISTER
    else -> UserStatus.NONE
}