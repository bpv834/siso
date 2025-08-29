package com.likelion.domain.login.repository

import com.likelion.domain.login.model.UserSignUpProfile
import java.io.IOException

interface SignUpRepository {
    suspend fun addProfile(
        refreshToken: String,
        profile: UserSignUpProfile
    )

    suspend fun addImage(imgPathList: List<String>)

    suspend fun addVoice(voicePath: String)

}