package com.likelion.data.login.repository

import com.likelion.data.login.mapper.toDomain
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.PostKakaoResult
import com.likelion.domain.login.model.User
import com.likelion.domain.login.repository.LoginRepository
import com.likelion.remote.api.KakaoAuthApiService
import com.likelion.remote.model.KakaoAccessTokenRequestDto
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoAuthApiService
) : LoginRepository {
    // 서버에 카카오 액세스 토큰을 보냄
    override suspend fun postKakaoAccessToken(token: String): PostKakaoResult {
        return try {
            val response = kakaoApi.postKakaoToken(KakaoAccessTokenRequestDto(accessToken = token))
            if (response.isSuccessful) {
                val body = response.body() ?: return PostKakaoResult.Error(code = 500, message = "Empty body")
                PostKakaoResult.Success(
                    body.toDomain()
                )
            } else {
                PostKakaoResult.Error(response.code(), response.message())
            }

        } catch (t: Throwable) {
            PostKakaoResult.Exception(t)
        }
    }

    // 서버에 리프래시 토큰 재발급
    override suspend fun postRefreshToken(token: BasicToken): User {
        val response = kakaoApi.refreshServerToken("Bearer ${token.refreshToken}")
        if (response.isSuccessful) {
            val body = response.body() ?: throw IllegalStateException("Empty refresh body")
            return body.toDomain()
        } else {
            throw IllegalStateException("HTTP ${response.code()} ${response.message()}")
        }
    }
}