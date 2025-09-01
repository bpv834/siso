package com.likelion.home.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.usecase.StartCallUseCase
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getTokenAllUseCase: GetTokenAllUseCase,
    private val startCallUseCase: StartCallUseCase
) : ViewModel() {

    // 외부에 노출되는 UI 상태 (데이터)
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.LoadingToken)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    // 외부에 노출되는 부수 효과 (일회성 이벤트)
    private val _sideEffect = MutableSharedFlow<HomeScreenSideEffect>()
    val sideEffect: SharedFlow<HomeScreenSideEffect> = _sideEffect.asSharedFlow()

    private var accessToken: String? = null

    init {
        onEvent(HomeScreenUiEvent.GetTokenAndLoadUsers)
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.GetTokenAndLoadUsers -> getTokenAndLoadUsers()
            is HomeScreenUiEvent.OnClickCallButton -> onClickCallButton(
                receiverId =  event.receiverId, accessToken = ""
            )
        }
    }

    private fun getTokenAndLoadUsers() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUiState.LoadingToken

            val userToken = getTokenAllUseCase.invoke().firstOrNull()?.accessToken

            if (userToken.isNullOrEmpty()) {
                _uiState.value = HomeScreenUiState.Error("토큰을 가져오지 못했습니다.")
            } else {
                accessToken = userToken
                loadUsers(userToken)
            }
        }
    }

    private fun loadUsers(token: String) {
        viewModelScope.launch {
            _uiState.value = HomeScreenUiState.LoadingUsers

            val result = getAllUsersUseCase.execute(token)

            if (result.isSuccess) {
                _uiState.value = HomeScreenUiState.Success(result.getOrThrow())
            } else {
                _uiState.value = HomeScreenUiState.Error(
                    result.exceptionOrNull()?.message ?: "유저 정보 로딩에 실패했습니다."
                )
            }
        }
    }

    private fun onClickCallButton(receiverId: Long, accessToken: String) {
        viewModelScope.launch {
            val token = accessToken
            if (token != null) {
                try {
                    startCallUseCase.execute(receiverId = receiverId, accessToken = accessToken)
                    _sideEffect.emit(HomeScreenSideEffect.NavigateToCaller(receiverId))
                } catch (e: Exception) {
                    _sideEffect.emit(HomeScreenSideEffect.ShowSnackbar("통화 실패: ${e.message}"))
                }
            } else {
                _sideEffect.emit(HomeScreenSideEffect.ShowSnackbar("유효한 토큰이 없어 통화를 시작할 수 없습니다."))
            }
        }
    }
}