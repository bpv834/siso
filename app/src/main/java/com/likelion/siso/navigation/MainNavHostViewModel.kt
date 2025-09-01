package com.likelion.siso.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.usecase.RejectCallUseCase
import com.likelion.domain.notification.model.Call
import com.likelion.domain.notification.usecase.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainNavHostViewModel @Inject constructor(
    private val rejectCallUseCase: RejectCallUseCase,
) : ViewModel() {
    /*    private val _uiState = MutableStateFlow(MainUiState())
        val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()*/

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    // FCM 이벤트가 오면 SharedFlow에 emit
    fun onFcmCallEvent(call: Call) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IncomingCall(call))
        }
    }

    // FCM 이벤트가 오면 유스케이스 실행 후 SharedFlow에 emit
    fun onFcmRejectEvent() { // 상대방이 거절
        viewModelScope.launch {
            // 상대방 거절시 나도 채널 탈출
            rejectCallUseCase.execute()
                .onSuccess { _uiEvent.emit(UiEvent.CallReject) }
                .onFailure { _uiEvent.emit(UiEvent.Error("채널 종료 실패")) }
        }
    }

    // 내가 직접 거절
    fun rejectCallByMe() {
        viewModelScope.launch {
            rejectCallUseCase.execute()
                .onSuccess { _uiEvent.emit(UiEvent.CallRejectedByMe) }
                .onFailure { _uiEvent.emit(UiEvent.Error("채널 종료 실패")) }
        }
    }
}