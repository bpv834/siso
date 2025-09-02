package com.likelion.domain.login.repository

import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.PostKakaoResult
import com.likelion.domain.login.model.User

// 서버
interface LoginRepository {
    // 서버에 카카오sdk에서 받은 accessToken을 넘김
    suspend fun postKakaoAccessToken(token: String): PostKakaoResult
    // 서버에 리프레시 토큰 받음
    suspend fun postRefreshToken(token: BasicToken): User
}