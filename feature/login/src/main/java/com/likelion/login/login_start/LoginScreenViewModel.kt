package com.likelion.login.login_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.usecase.FetchKakaoTokenUseCase
import com.likelion.domain.login.model.BasicToken
import com.likelion.domain.login.model.PostKakaoResult
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

    init {
        checkLocalToken()
        getTokenAll()
    }

    fun clearToken() {
        viewModelScope.launch {
            clearLocalTokenUseCase()
        }
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.CheckLocalToken -> TODO()
            LoginEvent.ClickLogin -> fetchKakaoToken()
        }
    }

    fun getTokenAll() {
        viewModelScope.launch {
            val result = getTokenAllUseCase()
            result.collect {
                Timber.d("로컬 액세스: ${it?.accessToken}")
                Timber.d("로컬 리프레시: ${it?.refreshToken}")
                Timber.d("로컬 유저상태: ${it?.userStatus}")
                Timber.d("로컬 유저정보: ${it?.userInfo}")
            }
        }
    }

    fun postRefreshToken(token: BasicToken) {
        viewModelScope.launch {
            Timber.d("리프래시 토큰 요청!!")
            val result = postRefreshTokenUseCase(token)
            Timber.d("서버에서 받은 액세스: ${result.accessToken}")
            Timber.d("서버에서 받은 리프레시: ${result.refreshToken}")
            Timber.d("서버에서 받은 유저상태: ${result.userStatus}")
            Timber.d("서버에서 받은 유저정보: ${result.userInfo}")
            _uiState.update {
                it.copy(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken,
                    user = result,
                )
            }
            Timber.d("uiState131: ${_uiState.value}")
        }
    }

    fun checkLocalToken() {
        viewModelScope.launch {
            val result = getTokenAllUseCase()
            result.collect {
                Timber.d("로컬 액세스: ${it?.accessToken}")
                Timber.d("로컬 리프레시: ${it?.refreshToken}")
                Timber.d("로컬 유저상태: ${it?.userStatus}")
                Timber.d("로컬 유저정보: ${it?.userInfo}")
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
                                  //    userState = UserStatus.REGISTER,
                                )
                            }
                            Timber.tag("LoginScreenViewModel").d("${postKakao.token}")
                            // 리프래시 토큰 요청
                            postRefreshToken(postKakao.token)
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

//
//    // 자동로그인 중복 방지 플래그
//    private var autoLoginTried = false
//
//
//    fun handleEvent(event: LoginEvent) {
//        when (event) {
//            LoginEvent.CheckLocalToken -> getLocalToken()
//            LoginEvent.ClickLogin -> fetchKakaoToken()
//        }
//    }
//
//
//    init {
//        autoLogin()
//    }
//
//    fun getTokenAll() {
//        viewModelScope.launch {
//            val user = getTokenAllUseCase().firstOrNull()
//            Timber.d("저장된 User 전체: $user")
//        }
//    }
//
//    fun autoLogin() {
//        viewModelScope.launch {
//            if (autoLoginTried) {
//                Timber.d("자동로그인: 이미 시도함")
//                return@launch
//            }
//            // 1) refresh/state는 가벼운 로컬 토큰 소스에서 읽는다 (전체 User가 없을 수 있음)
//            val local = getLocalTokenUseCase().firstOrNull()
//            val refresh = local?.refreshToken
//            val status = local?.userStatus
//            Timber.d("로컬토큰확인(자동로그인): refresh=$refresh, status=$status")
//
//            // 2) access 보유 여부만 전체 User에서 보조 확인 (있다면 굳이 refresh 치지 않음)
//            val full = getTokenAllUseCase().firstOrNull()
//            val hasAccess = !full?.accessToken.isNullOrBlank()
//
//            if (!refresh.isNullOrBlank() && status == UserStatus.LOGIN && !hasAccess) {
//                autoLoginTried = true
//                postRefreshToken(
//                    BasicToken(
//                        refreshToken = refresh,
//                        userStatus = status
//                    )
//                )
//            } else {
//                Timber.d("자동로그인 스킵: refresh=$refresh, status=$status, hasAccess=$hasAccess")
//            }
//        }
//    }
//
//
//    fun goLogin() {
//        viewModelScope.launch {
//            _uiState.update {
//                it.copy(
//                    refreshToken = "이미회원",
//                    userState = UserStatus.LOGIN,
//                    error = null
//                )
//            }
//        }
//    }
//
//    fun clearDataStore() {
//        viewModelScope.launch {
//            clearLocalTokenUseCase()
//            _uiState.update {
//                it.copy(
//                    refreshToken = null,
//                    userState = UserStatus.NONE,
//                    error = null
//                )
//            }
//            Timber.d("데이터스토어 초기화 ${_uiState.value}")
//        }
//    }
//
//    private fun postRefreshToken(token: BasicToken) {
//        viewModelScope.launch {
//            try {
//                autoLoginTried = true
//                val result = postRefreshTokenUseCase(token = token)
//
//                _uiState.update {
//                    it.copy(
//                        accessToken = result.accessToken,
//                        refreshToken = result.refreshToken,
//                        userState = result.userStatus,
//                        error = null,
//                    )
//                }
//                // refresh 응답은 access/refresh/status/user 전체 제공 → saveTokenAll
//                saveRefreshToken(
//                    BasicToken(
//                        refreshToken = result.refreshToken,
//                        userStatus = result.userStatus
//                    )
//                )
//                saveTokenAll(result)
//                Timber.d("postRefresh: ${_uiState.value}")
//            } catch (e: Exception) {
//                val msg = e.message ?: "네트워크 오류"
//                if (msg.contains("401") || msg.contains("unauthorized", ignoreCase = true)) {
//                    clearDataStore()
//                    _uiState.update { it.copy(error = "인증이 만료되었습니다. 다시 로그인 해주세요.") }
//                } else {
//                    _uiState.update { it.copy(error = "네트워크 오류: $msg") }
//                }
//            }
//        }
//    }
//
//    fun saveRefreshToken(token: BasicToken) {
//        viewModelScope.launch {
//            saveRefreshTokenUseCase(token)
//        }
//    }
//
//    fun saveTokenAll(user: User) {
//        viewModelScope.launch {
//            saveTokenAllUseCase(user)
//
//        }
//    }
//
//    fun postKakaoAccessToken(token: String) {
//        viewModelScope.launch {
//            Timber.d("서버로 카카오토큰 보냄")
//            when (val result = postKakaoAccessTokenUseCase(token = token)) {
//                is PostKakaoResult.Success -> {
//                    // 자동로그인 수행
//                    // 만약 회원인경우 자동로그인 시도...
//                    //  postRefreshTokenUseCase(result.token)
//                    _uiState.update {
//                        it.copy(
//                            refreshToken = result.token.refreshToken,
//                            userState = result.token.userStatus,
//                            error = null,
//                        )
//                    }
//                    Timber.d(" PostKakaoResult.Success: ${_uiState.value}")
//                    // postKakaoAccess 성공 시점에는 서버가 refresh + 상태만 내려주므로 saveToken만 수행
//                    saveRefreshToken(
//                        BasicToken(
//                            refreshToken = result.token.refreshToken,
//                            userStatus = result.token.userStatus
//                        )
//                    )
//                    // ※ postRefresh는 별도 흐름(자동로그인 또는 명시적 로그인)에서 호출하여
//                    //    access/refresh/status/user 전체를 받은 뒤 saveTokenAll로 저장한다.
//                }
//
//
//                is PostKakaoResult.Error -> {
//                    when (result.code) {
//                        401 -> {
//                            Timber.d("401Error -> 데이터 초기화")
//                            clearDataStore()
//                        } // 로컬 값 초기화
//                        404 -> {
//                            _uiState.update {
//                                it.copy(
//                                    error = "서버 오류"
//                                )
//                            }
//                            Timber.d("404Error -> ${_uiState.value.error}")
//                        } // 서버 없음
//                    }
//                }
//
//                is PostKakaoResult.Exception -> {
//                    _uiState.update { it.copy(error = ("네트워크 오류")) }
//                }
//            }
//
//        }
//    }
//
//    // 1번 로컬 토큰 확인하기
//    fun getLocalToken() {
//        viewModelScope.launch {
//            val token = getLocalTokenUseCase().firstOrNull()
//            Timber.d("로컬토큰확인: $token")
//            _uiState.update { it.copy(refreshToken = token?.refreshToken, userState = token?.userStatus ?: UserStatus.NONE) }
//        }
//    }
//
//
//    // 카카오 액세스토큰 발급
//    fun fetchKakaoToken() {
//        viewModelScope.launch {
//            when (val result = fetchKakaoTokenUseCase()) {
//                // 카카오 액세스토큰 발급에 성공했을 때
//                is KakaoTokenResult.Success -> {
//                    _uiState.update { current ->
//                        current.copy(
//                            accessToken = result.token,
//                            error = null
//                        )
//                    }
//                    Timber.d("카카오 액세스 토큰 발급 성공${_uiState.value}")
//                    // 서버에 카카오 post
//                    postKakaoAccessToken(result.token)
//                }
//
//                KakaoTokenResult.Canceled -> {
//                    _uiState.update { current ->
//                        current.copy(
//                            error = "로그인 취소"
//                        )
//                    }
//                    Timber.d("카카오 Canceled: ${_uiState.value.error}")
//                }
//
//                is KakaoTokenResult.Error -> {
//                    _uiState.update { current ->
//                        current.copy(
//                            error = result.cause.message ?: "알 수 없는 오류"
//                        )
//                    }
//                    Timber.d("카카오 Error: ${_uiState.value.error}")
//                }
//            }
//
//        }
//    }
}