package com.likelion.login.login_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.ExchangeKakaoTokenUseCase
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
    private val fetchKakaoSdkToken: ExchangeKakaoTokenUseCase,

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
//        checkLocalToken()
        checkLocalTokenTest()
        sendFcmToken()
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.CheckLocalToken -> checkLocalTokenTest()//checkLocalToken()
            LoginEvent.ClickLogin -> refreshTokenTest()//fetchKakaoToken()
        }
    }

    // 로컬 토큰 확인
    fun checkLocalTokenTest() {
        viewModelScope.launch {
            val local = getLocalTokenUseCase().firstOrNull()
            if (local == null) {
                Timber.d("로컬 정보가 비어있음")
                return@launch
            }
            Timber.d("로컬 access: ${local.accessToken}")
            Timber.d("로컬 refresh: ${local.refreshToken}")
            Timber.d("로컬 status: ${local.userStatus}")
            Timber.d("로컬 hasProfile: ${local.hasProfile}")

            // 토큰이 있으면 자동로그인 시도
            autoLogin(local.refreshToken)
        }
    }

    // 자동로그인 로직
    fun autoLogin(refreshToken: String) {
        viewModelScope.launch {
            Timber.d("자동로그인 로직 시작")
            val token = postRefreshTokenUseCase(refreshToken)
            Timber.d("서버에서 받은 access: ${token.accessToken}")
            Timber.d("서버에서 받은 refresh: ${token.refreshToken}")
            Timber.d("서버에서 받은 status: ${token.userStatus}")
            Timber.d("서베에서 받은 userInfo: ${token.userInfo}")
            Timber.d("서버에서 받은 hasProfile: ${token.hasProfile}")
            saveTokenAll(
                user = User(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken,
                    userStatus = token.userStatus,
                    userInfo = token.userInfo,
                    hasProfile = token.hasProfile
                )
            )
            _uiState.update {
                it.copy(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken,
                    userState = token.userStatus,
                    hasProfile = token.hasProfile,
                )
            }
        }
    }

    // 리프래시 토큰 발급 최초
    fun refreshTokenTest() {
        viewModelScope.launch {
            Timber.d("리프래시 토큰 발급 최초")
            val kakaoToken = fetchKakaoSdkToken()
            when (kakaoToken) {
                is KakaoTokenResult.Success -> {
                    val access = postKakaoAccessTokenUseCase(
                        token = kakaoToken.token
                    )
                    when (access) {
                        is PostKakaoResult.Success -> {
                            // 서버에서 api/auth/kakao 를 성공 했을 경우!
                            val fresh = postRefreshTokenUseCase(access.token.refreshToken)
                            Timber.d("서버에서 받은 access: ${fresh.accessToken}")
                            Timber.d("서버에서 받은 refresh: ${fresh.refreshToken}")
                            Timber.d("서버에서 받은 status: ${fresh.userStatus}")
                            Timber.d("서버에서 받은 userInfo: ${fresh.userInfo}")
                            Timber.d("서버에서 받은 hasProfile: ${fresh.hasProfile}")

                            // Local 저장
                            saveTokenAll(
                                user = User(
                                    accessToken = fresh.accessToken,
                                    refreshToken = fresh.refreshToken,
                                    userStatus = fresh.userStatus,
                                    userInfo = fresh.userInfo,
                                    hasProfile = fresh.hasProfile
                                )
                            )

                            _uiState.update {
                                it.copy(
                                    accessToken = fresh.accessToken,
                                    refreshToken = fresh.refreshToken,
                                    userState = fresh.userStatus,
                                    hasProfile = fresh.hasProfile
                                )
                            }

                        }

                        is PostKakaoResult.Error -> {
                            Timber.d("서버 Error: ${access.message}")
                            _uiState.update {
                                it.copy(
                                    error = access.message
                                )
                            }
                        }

                        is PostKakaoResult.Exception -> {
                            "서버 Exception: ${access.throwable.message}"
                            _uiState.update {
                                it.copy(
                                    error = access.throwable.message
                                )
                            }
                        }
                    }
                }

                KakaoTokenResult.Canceled -> Timber.d("카카오 취소")
                is KakaoTokenResult.Error -> Timber.d("카카오 에러: ${kakaoToken.cause.message}")
            }
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

//    fun postRefreshToken(token: BasicToken) {
//        viewModelScope.launch {
//            try {
//                Timber.d("리프래시 토큰 요청!!")
//                val result = postRefreshTokenUseCase(token)
//                saveTokenAll(result)
//                Timber.d("access: ${result.accessToken}")
//                Timber.d("refresh: ${result.refreshToken}")
//
//                _uiState.update {
//                    it.copy(
//                        accessToken = result.accessToken,
//                        refreshToken = result.refreshToken,
//                        userState = result.userStatus,
//                        hasProfile = result.hasProfile,
//                    )
//                }
//                // 3) 저장소에서 fcm 토큰 얻어와 서버에 전송 호준
//                sendFcmToken()
//            } catch (e: Exception) {
//                val msg = e.message.orEmpty()
//                if (msg.contains("401", true) || msg.contains("unauthorized", true)) {
//                    clearToken()
//                    _uiState.update { it.copy(error = "인증이 만료되었습니다. 다시 로그인 해주세요.") }
//                } else {
//                    _uiState.update { it.copy(error = "네트워크 오류: ${msg.ifBlank { "잠시 후 다시 시도해주세요." }}") }
//                }
//            }
//        }
//    }

//    fun checkLocalToken() {
//        viewModelScope.launch {
//            val result = getTokenAllUseCase().firstOrNull()
//            if (result != null) {
//                Timber.d("로컬 정보가 비어있지 않음")
//                val user = postRefreshTokenUseCase(
//                    BasicToken(
//                        accessToken = result.accessToken,
//                        refreshToken = result.refreshToken,
//                        userStatus = result.userStatus,
//                        hasProfile = result.hasProfile
//                    )
//                )
//                Timber.d("로컬토큰 확인 토큰 재발행 $user")
//                saveRefreshTokenUseCase(
//                    BasicToken(
//                        accessToken = result.accessToken,
//                        refreshToken = result.refreshToken,
//                        userStatus = result.userStatus,
//                        hasProfile = result.hasProfile
//                    )
//                )
//                saveTokenAll(user)
//                Timber.d("이전 값: ${result.refreshToken}")
//                _uiState.update {
//                    it.copy(
//                        accessToken = user.accessToken,
//                        refreshToken = user.refreshToken,
//                        userState = user.userStatus,
//                        hasProfile = user.hasProfile,
//                    )
//                }
//                Timber.d("이후 값: ${_uiState.value.refreshToken}")
//            } else {
//                Timber.d("로컬 정보가 비어있음")
//            }
//        }
//    }


    // Fcm 토큰을 서버로 전송하는 메서드
    private fun sendFcmToken() {
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