package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.usecase.StartCallUseCase
import com.likelion.domain.home.usecase.ChangeDialogStatusUseCase
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.home.usecase.GetDialogStatusUseCase
import com.likelion.domain.login.usecase.GetLocalTokenUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val startCallUseCase: StartCallUseCase,
    private val getDialogStatusUseCase: GetDialogStatusUseCase,
    private val changeDialogStatusUseCase: ChangeDialogStatusUseCase
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

        }
    }

    private fun changeDialogStatus(isDialog: Boolean) {
        _showHomeDialog.value = isDialog
        _uiStateHomePage.update { it.copy(isDialog = isDialog) }

        viewModelScope.launch {
            runCatching { changeDialogStatusUseCase(isDialog) }
                .onFailure { e -> Timber.e(e, "다이얼로그 상태 변경 실패") }
        }
    }

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
}