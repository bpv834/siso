package com.likelion.login.login_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.PostKakaoResult
import com.likelion.domain.login.model.User
import com.likelion.domain.login.model.UserStatus
import com.likelion.domain.login.usecase.ClearLocalTokenUseCase
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.login.usecase.PostKakaoAccessTokenUseCase
import com.likelion.domain.login.usecase.PostRefreshTokenUseCase
import com.likelion.domain.login.usecase.SaveRefreshTokenUseCase
import com.likelion.domain.login.usecase.SaveTokenAllUseCase
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

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.CheckLocalToken -> getLocalToken()
            LoginEvent.ClickLogin -> fetchKakaoToken()
        }
    }


    init {
        autoLogin()
    }

    fun autoLogin() {
        viewModelScope.launch {
            val user = getTokenAllUseCase().firstOrNull()
            Timber.d("저장된 User 전체: $user")
            val refresh = user?.refreshToken
            val status = user?.userStatus

            if (!refresh.isNullOrBlank() && status == UserStatus.LOGIN) {
                postRefreshToken(
                    BasicToken(
                        refreshToken = refresh,
                        userStatus = status
                    )
                )
            } else {
                Timber.d("자동로그인 스킵: refresh: $refresh, status: $status")
            }
        }
    }


    fun goLogin() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    refreshToken = "이미회원",
                    userState = UserStatus.LOGIN,
                    error = null
                )
            }
        }
    }

    fun clearDataStore() {
        viewModelScope.launch {
            clearLocalTokenUseCase()
            _uiState.update {
                it.copy(
                    refreshToken = null,
                    userState = UserStatus.NONE,
                    error = null
                )
            }
            Timber.d("데이터스토어 초기화 ${_uiState.value}")
        }
    }

    private fun postRefreshToken(token: BasicToken) {
        viewModelScope.launch {
            try {
                val result = postRefreshTokenUseCase(token = token)

                _uiState.update {
                    it.copy(
                        refreshToken = result.refreshToken,
                        userState = result.userStatus,
                        error = null,
                    )
                }
                saveRefreshToken(
                    BasicToken(
                        refreshToken = result.refreshToken,
                        userStatus = result.userStatus
                    )
                )
                saveTokenAll(result)
                Timber.d("postRefresh: ${_uiState.value}")
            } catch (e: Exception) {
                val msg = e.message ?: "네트워크 오류"
                // 401 등 인증 문제면 로컬 초기화
                if (msg.contains("401") || msg.contains("unauthorized", ignoreCase = true)) {
                    clearDataStore()
                    _uiState.update { it.copy(error = "인증이 만료되었습니다. 다시 로그인 해주세요.") }
                } else {
                    _uiState.update { it.copy(error = "네트워크 오류: $msg") }
                }

            }
        }
    }

    fun saveRefreshToken(token: BasicToken) {
        viewModelScope.launch {
            saveRefreshTokenUseCase(token)
        }
    }

    fun saveTokenAll(user: User) {
        viewModelScope.launch {
            saveTokenAllUseCase(user)

        }
    }

    fun postKakaoAccessToken(token: String) {
        viewModelScope.launch {
            Timber.d("서버로 카카오토큰 보냄")
            when (val result = postKakaoAccessTokenUseCase(token = token)) {
                is PostKakaoResult.Success -> {
                    // 자동로그인 수행
                    // 만약 회원인경우 자동로그인 시도...
                    //  postRefreshTokenUseCase(result.token)
                    _uiState.update {
                        it.copy(
                            refreshToken = result.token.refreshToken,
                            userState = result.token.userStatus,
                            error = null,
                        )
                    }
                    Timber.d(" PostKakaoResult.Success: ${_uiState.value}")
                    if (result.token.userStatus == UserStatus.LOGIN) {
                        // 자동로그인 실시
                        Timber.d("자동로그인 실시 ${_uiState.value}")
                        postRefreshToken(result.token)
                    }
                }


                is PostKakaoResult.Error -> {
                    when (result.code) {
                        401 -> {
                            Timber.d("401Error -> 데이터 초기화")
                            clearDataStore()
                        } // 로컬 값 초기화
                        404 -> {
                            _uiState.update {
                                it.copy(
                                    error = "서버 오류"
                                )
                            }
                            Timber.d("404Error -> ${_uiState.value.error}")
                        } // 서버 없음
                    }
                }

                is PostKakaoResult.Exception -> {
                    _uiState.update { it.copy(error = ("네트워크 오류")) }
                }
            }

        }
    }

    // 1번 로컬 토큰 확인하기
    fun getLocalToken() {
        viewModelScope.launch {
            val token = getLocalTokenUseCase().firstOrNull()
            Timber.d("로컬토큰확인: $token")
            _uiState.update { it.copy(refreshToken = token?.refreshToken, userState = token?.userStatus ?: UserStatus.NONE) }
        }
    }


    // 카카오 액세스토큰 발급
    fun fetchKakaoToken() {
        viewModelScope.launch {
            when (val result = fetchKakaoTokenUseCase()) {
                // 카카오 액세스토큰 발급에 성공했을 때
                is KakaoTokenResult.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            accessToken = result.token,
                            error = null
                        )
                    }
                    Timber.d("카카오 액세스 토큰 발급 성공${_uiState.value}")
                    // 서버에 카카오 post
                    postKakaoAccessToken(result.token)
                }

                KakaoTokenResult.Canceled -> {
                    _uiState.update { current ->
                        current.copy(
                            error = "로그인 취소"
                        )
                    }
                    Timber.d("카카오 Canceled: ${_uiState.value.error}")
                }

                is KakaoTokenResult.Error -> {
                    _uiState.update { current ->
                        current.copy(
                            error = result.cause.message ?: "알 수 없는 오류"
                        )
                    }
                    Timber.d("카카오 Error: ${_uiState.value.error}")
                }
            }

        }
    }
}