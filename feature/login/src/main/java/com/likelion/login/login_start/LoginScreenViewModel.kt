package com.likelion.login.login_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.PostKakaoResult
import com.likelion.domain.login.model.User
import com.likelion.domain.login.usecase.ClearLocalTokenUseCase
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.login.usecase.PostKakaoAccessTokenUseCase
import com.likelion.domain.login.usecase.PostRefreshTokenUseCase
import com.likelion.domain.login.usecase.SaveRefreshTokenUseCase
import com.likelion.domain.login.usecase.SaveTokenAllUseCase
import com.likelion.domain.notification.usecase.GetFcmTokenUseCase
import com.likelion.domain.notification.usecase.SaveFcmTokenUseCase
import com.likelion.login.event.LoginEvent
import com.likelion.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    // 카카오 accessToken 가져오기
    private val fetchKakaoTokenUseCase: FetchKakaoTokenUseCase,
    // 로컬 토큰 가져오기
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    // 카카오 액세스 토큰 서버에 보내기
    private val postKakaoAccessTokenUseCase: PostKakaoAccessTokenUseCase,
    // 리프래시 토큰 재발급
    private val postRefreshTokenUseCase: PostRefreshTokenUseCase,
    private val clearLocalTokenUseCase: ClearLocalTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,

    private val saveTokenAllUseCase: SaveTokenAllUseCase,
    private val getTokenAllUseCase: GetTokenAllUseCase,

    private val sendFcmTokenUseCase: SaveFcmTokenUseCase, // 서버로 fcm 토큰, user Id 보내는 메서드
    private val getFcmTokenUseCase: GetFcmTokenUseCase, // dataStore 에서 fcm 토큰을 가져오는 메서드

) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkLocalToken()
        sendFcmToken()
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.CheckLocalToken -> checkLocalToken()
            LoginEvent.ClickLogin -> fetchKakaoToken()
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            clearLocalTokenUseCase()
        }
    }

    private suspend fun saveTokenAll(user: User) {
        saveTokenAllUseCase(user)
    }

    fun postRefreshToken(token: BasicToken) {
        viewModelScope.launch {
            try {
                Timber.d("리프래시 토큰 요청!!")
                val result = postRefreshTokenUseCase(token)
                Timber.d("서버에서 받은 액세스: ${result.accessToken}")
                Timber.d("서버에서 받은 리프레시: ${result.refreshToken}")
                Timber.d("서버에서 받은 유저상태: ${result.userStatus}")
                Timber.d("서버에서 받은 유저정보: ${result.userInfo}")
                // 1) 로컬 저장을 먼저 완료 (레이스 방지)
                saveTokenAll(result)

                // 2) 그 다음 UI 상태 반영
                _uiState.update {
                    it.copy(
                        accessToken = result.accessToken,
                        refreshToken = result.refreshToken,
                        userState = result.userStatus,
                        hasProfile = result.hasProfile,
                    )
                }
                // 3) 저장소에서 fcm 토큰 얻어와 서버에 전송 호준
                sendFcmToken()
            } catch (e: Exception) {
                val msg = e.message.orEmpty()
                if (msg.contains("401", true) || msg.contains("unauthorized", true)) {
                    clearToken()
                    _uiState.update { it.copy(error = "인증이 만료되었습니다. 다시 로그인 해주세요.") }
                } else {
                    _uiState.update { it.copy(error = "네트워크 오류: ${msg.ifBlank { "잠시 후 다시 시도해주세요." }}") }
                }
            }
        }
    }

    fun checkLocalToken() {
        viewModelScope.launch {
            val result = getTokenAllUseCase().firstOrNull()
            Timber.d("로컬 저장소 확인...")
            Timber.d("로컬 액세스: ${result?.accessToken}")
            Timber.d("로컬 리프래시: ${result?.refreshToken}")
            Timber.d("로컬 유저상태 : ${result?.userStatus}")
            Timber.d("로컬 프로필상태 : ${result?.hasProfile}")
            Timber.d("로컬 유저정보 : ${result?.userInfo}")
            if (result != null) {
                Timber.d("로컬 정보가 비어있지 않음")
                val user = postRefreshTokenUseCase(
                    BasicToken(
                        accessToken = result.accessToken,
                        refreshToken = result.refreshToken,
                        userStatus = result.userStatus,
                        hasProfile = result.hasProfile
                    )
                )
                Timber.d("로컬토큰 확인 토큰 재발행 $user")
                saveRefreshTokenUseCase(
                    BasicToken(
                        accessToken = result.accessToken,
                        refreshToken = result.refreshToken,
                        userStatus = result.userStatus,
                        hasProfile = result.hasProfile
                    )
                )
                saveTokenAll(user)
                Timber.d("이전 값: ${result.refreshToken}")
                _uiState.update {
                    it.copy(
                        accessToken = user.accessToken,
                        refreshToken = user.refreshToken,
                        userState = user.userStatus,
                        hasProfile = user.hasProfile,
                    )
                }
                Timber.d("이후 값: ${_uiState.value.refreshToken}")
            } else {
                Timber.d("로컬 정보가 비어있음")
            }
        }
    }

    // 1. 카카오한테 액세스 토큰을 받음
    fun fetchKakaoToken() {
        viewModelScope.launch {
            val result = fetchKakaoTokenUseCase()
            when (result) {
                is KakaoTokenResult.Success -> {
                    Timber.d("카카오한테 액세스 토큰 받음 ${result.token}")
                    // 2. 액세스 토큰을 서버에 던져준다.
                    val postKakao = postKakaoAccessTokenUseCase(result.token)
                    // 3. 서버에서 받은 값을 update한다.
                    when (postKakao) {
                        is PostKakaoResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    refreshToken = postKakao.token.refreshToken,
                                    userState = postKakao.token.userStatus,
                                    hasProfile = postKakao.token.hasProfile
                                )
                            }
                            Timber.tag("LoginScreenViewModel").d("${postKakao.token}")
                            // 리프래시 토큰 요청
                            postRefreshToken(postKakao.token)
                            // fcm 토큰을 가져와 서버에 전송한다

                        }

                        is PostKakaoResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = postKakao.message ?: "error"
                                )
                            }
                        }

                        is PostKakaoResult.Exception -> {
                            _uiState.update {
                                it.copy(
                                    error = postKakao.throwable.message
                                )
                            }
                        }
                    }
                }

                is KakaoTokenResult.Error -> {
                    Timber.d("카카오 Error: ${result.cause}")
                }

                KakaoTokenResult.Canceled -> {
                    Timber.d("카카오 Canceled")
                }

            }
        }
    }

    // Fcm 토큰을 서버로 전송하는 메서드
    fun sendFcmToken() {
        viewModelScope.launch {
            try {
                val fcmToken: String? = getFcmTokenUseCase().firstOrNull()
                Timber.d("fcmToken: $fcmToken")
                fcmToken?.let {
                    sendFcmTokenUseCase(it)
                }
            } catch (e: Exception) {
                Timber.e(e, "FCM 토큰 전송 실패")
            }
        }
    }
}