package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.usecase.ChangeDialogStatusUseCase
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.home.usecase.GetDialogStatusUseCase
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.model.NotificationModel
import com.likelion.domain.notification.usecase.GetFcmTokenUseCase
import com.likelion.domain.notification.usecase.SendFcmTokenUseCase
import com.likelion.domain.notification.usecase.UpdateUserAllowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val getDialogStatusUseCase: GetDialogStatusUseCase,
    private val changeDialogStatusUseCase: ChangeDialogStatusUseCase,
    private val updateUserAllowUseCase: UpdateUserAllowUseCase,
    private val sendFcmTokenUseCase: SendFcmTokenUseCase,
    private val getFcmTokenUseCase : GetFcmTokenUseCase,


) : ViewModel() {

    // 외부에 노출되는 UI 상태 (데이터)
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.LoadingToken)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _uiStateHomePage = MutableStateFlow(HomePageUiState())
    val uiStateHomePage: StateFlow<HomePageUiState> = _uiStateHomePage.asStateFlow()

    private val _showHomeDialog = MutableStateFlow(false)
    val showHomeDialog: StateFlow<Boolean> = _showHomeDialog.asStateFlow()


    // 외부에 노출되는 부수 효과 (일회성 이벤트)
    private val _sideEffect = MutableSharedFlow<HomeScreenSideEffect>()
    val sideEffect: SharedFlow<HomeScreenSideEffect> = _sideEffect.asSharedFlow()

    private var accessToken: String? = null

    init {
        onEvent(HomeScreenUiEvent.GetTokenAndLoadUsers)
        getDialogStatus()
        uploadFcmToken()


    }

    // 저장소에 있는 fcm 토큰을 서버에 id와 매핑하기 위해 보내는 메서드
    private  fun uploadFcmToken() {
        viewModelScope.launch {
            // 1. 저장소에서 FCM 토큰을 가져옵니다.
            val fcmTokenString = getFcmTokenUseCase.invoke().firstOrNull()

            // 2. 토큰이 null이거나 비어있는지 확인하여 예외를 방지합니다.
            if (fcmTokenString.isNullOrBlank()) {
                Timber.d("FCM 토큰을 찾을 수 없습니다. 업로드를 건너뜁니다.")

            }

            // 3. 토큰이 존재하면 데이터 모델 객체를 생성합니다.
            val fcmToken = FcmToken(token = fcmTokenString?:"")

            // 4. UseCase를 호출하여 서버에 토큰을 전송합니다.
            val result = sendFcmTokenUseCase.invoke(fcmToken)

            // 5. 'Result' 객체의 성공/실패 여부에 따라 로직을 분기합니다.
            if (result.isSuccess) {
                Timber.d("FCM 토큰이 성공적으로 업로드되었습니다.")
            } else {
                val exception = result.exceptionOrNull()
                Timber.e(exception, "FCM 토큰 업로드 실패")
            }
        }

    }
    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.GetTokenAndLoadUsers -> {
                Timber.d("getTokenAndLoadUsers")
                getTokenAndLoadUsers()
            }

            is HomeScreenUiEvent.OnClickCallButton -> {
                Timber.d("HomeScreenUiEvent.onClickCallButton ")
                onClickCallButton(
                    receiverId = event.receiverId,
                )
            }

            is HomeScreenUiEvent.ChangeDialogStatus -> changeDialogStatus(event.isDialog)
            HomeScreenUiEvent.GetDialogStatus -> getDialogStatus()

            is HomeScreenUiEvent.PermissionChanged -> {
                updateUserAllow(event.isGranted)
            }
        }
    }

    // 온보딩 상태 상태 바꾸기
    private fun changeDialogStatus(isDialog: Boolean) {
        _showHomeDialog.value = isDialog
        _uiStateHomePage.update { it.copy(isDialog = isDialog) }

        viewModelScope.launch {
            runCatching { changeDialogStatusUseCase(isDialog) }
                .onFailure { e -> Timber.e(e, "다이얼로그 상태 변경 실패") }
        }
    }

    // 온보딩 팝업띄우기
    private fun getDialogStatus() {
        viewModelScope.launch {
            val skip = try {
                getDialogStatusUseCase().firstOrNull() ?: false
            } catch (e: Exception) {
                Timber.e("Error: ${e.message}")
                false
            }
            _uiStateHomePage.update { it.copy(isDialog = skip) }
            Timber.d("다이얼로그 상태: $skip")
        }
    }

    // 로컬 토큰 가져오고 유저부르기
    private fun getTokenAndLoadUsers() {
        viewModelScope.launch {
            Timber.d("🔵 [getTokenAndLoadUsers] 토큰 로딩 시작")
            _uiState.value = HomeScreenUiState.LoadingToken

            // → 이렇게 하면 null 값은 스킵하고, 실제 User 객체가 나올 때까지 대기합니다.
            val tokenInfo = getLocalTokenUseCase().firstOrNull()
            Timber.d("🟡 [getTokenAndLoadUsers] 가져온 토큰 = ${tokenInfo?.accessToken}")

            if (!tokenInfo?.accessToken.isNullOrEmpty()) {
                accessToken = tokenInfo.accessToken
                loadUsers(accessToken!!)
            }
        }
    }

    // 매칭유저 목록 불러오기
    private fun loadUsers(token: String) {
        viewModelScope.launch {
            _uiState.value = HomeScreenUiState.LoadingUsers

            val result = getAllUsersUseCase.execute(token)
            Timber.d("result = ${result}")

            if (result.isSuccess) {
                _uiState.value = HomeScreenUiState.Success(result.getOrThrow())
            } else {
                _uiState.value = HomeScreenUiState.Error(
                    result.exceptionOrNull()?.message ?: "유저 정보 로딩에 실패했습니다."
                )
            }
        }
    }

    // 전화버튼 누를때 서버와 통신하는 메서드
    private fun onClickCallButton(receiverId: Long) {
        Timber.d("onClickCallButton receiverId : $receiverId / accessToken : $accessToken")
        viewModelScope.launch {
            try {
                // startCallUseCase.execute(receiverId = receiverId, accessToken = accessToken!!)
                _sideEffect.emit(HomeScreenSideEffect.NavigateToCaller(receiverId))
            } catch (e: Exception) {
                _sideEffect.emit(HomeScreenSideEffect.ShowSnackbar("통화 실패: ${e.message}"))
            }
        }
    }

    // 사용자 동의 현황 서버에 전송하기
    private fun updateUserAllow(isAllow: Boolean) {
        viewModelScope.launch {
            val notificationModel = NotificationModel(
                subscribed = isAllow
            )
            val token = getLocalTokenUseCase().firstOrNull()?.accessToken
            updateUserAllowUseCase(
                accessToken = "Bearer $token",
                notificationModel = notificationModel
            )
        }
    }
}