package com.likelion.login.login_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.ExchangeKakaoTokenUseCase
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
import com.likelion.domain.onboarding.usecase.GetOnBoardingSkipUseCase
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
    // 토큰 전체 지우기
    private val clearLocalTokenUseCase: ClearLocalTokenUseCase,
    // 토큰 정보 로컬에 저장
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    //
    private val saveTokenAllUseCase: SaveTokenAllUseCase,

    private val sendFcmTokenUseCase: SaveFcmTokenUseCase, // 서버로 fcm 토큰, user Id 보내는 메서드
    private val getFcmTokenUseCase: GetFcmTokenUseCase, // dataStore 에서 fcm 토큰을 가져오는 메서드
    // 온보딩상태 가져오기
    private val getOnBoardingSkipUseCase: GetOnBoardingSkipUseCase,

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
            LoginEvent.ClickLogin -> refreshTokenTest()
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

    var flag = false
    fun checkLocalToken() {
        viewModelScope.launch {
            val skip = try {
                getOnBoardingSkipUseCase().firstOrNull() ?: false
            } catch (e: Exception) {
                Timber.e("Error: ${e.message}")
                false
            }
            _uiState.update { it.copy(isSkip = skip) }
            Timber.d("온보딩 스킵 상태: $skip")

            val result = getLocalTokenUseCase().firstOrNull()
            Timber.d("로컬 저장소 확인...")
            Timber.d("로컬 액세스: ${result?.accessToken}")
            Timber.d("로컬 리프래시: ${result?.refreshToken}")
            Timber.d("로컬 유저상태 : ${result?.userStatus}")
            Timber.d("로컬 프로필상태 : ${result?.hasProfile}")
            if (result != null &&!flag) {
                flag = true
                Timber.d("로컬 정보가 비어있지 않음")
                val user = postRefreshTokenUseCase(result.refreshToken)
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
                            saveRefreshTokenUseCase(
                                token = BasicToken(
                                    accessToken = fresh.accessToken,
                                    refreshToken = fresh.refreshToken,
                                    userStatus = fresh.userStatus,
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
                            Timber.d("서버 Error: ${access.code}, ${access.message}")
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