package com.likelion.siso.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.call_for_caller.model.CallModel
import com.likelion.domain.call_for_caller.usecase.DenyCallUseCase
import com.likelion.domain.call_for_caller.usecase.RejectCallUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.notification.model.Call
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel
@HiltViewModel
class MainNavHostViewModel @Inject constructor(
    private val rejectCallUseCase: RejectCallUseCase,
    private val denyCallUseCase: DenyCallUseCase,
    private val getTokenAllUseCase: GetTokenAllUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    // 상대방이 거절
    fun onFcmRejectEvent() {
        viewModelScope.launch {
            // 채널 나가고 거절됐다는 아고라 이벤트 발행
            rejectCallUseCase.execute()
                .onSuccess { _uiEvent.emit(UiEvent.CallReject) }
                .onFailure { _uiEvent.emit(UiEvent.Error("채널 종료 실패")) }
        }
    }


    // FCM 이벤트가 오면 SharedFlow에 emit
    fun onFcmCallEvent(call: Call) {
        viewModelScope.launch { _uiEvent.emit(UiEvent.IncomingCall(call)) }
    }

    // 팝업에서 수신자가 전화 받기 클릭
    // 화면 전환 이벤트 발행
    fun acceptIncomingCall(call: Call) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateToReceiverScreen(call = call))
        }
    }

    // 팝업에서 수신자가(사용자) 전화 거절 클릭
    fun rejectIncomingCall(call: Call) {
        viewModelScope.launch {
            val token = getTokenAllUseCase()
                .map { it?.accessToken }
                .first { it != null }!!

            val userId = getTokenAllUseCase()
                .map { it?.userInfo?.id }
                .first { it != null }!!


            denyCallUseCase.execute(
                accessToken = token,
                request = callMapper(call = call, receiverId = userId)
            )
                .onSuccess {
                    // 내가 거절한것이 성공했다는것을 ui 에 알림
                    _uiEvent.emit(
                        UiEvent.CallRejectedByMe
                    )
                }
                .onFailure { _uiEvent.emit(UiEvent.Error("채널 종료 실패")) }
        }
    }

    fun callMapper(call: Call, receiverId: Long): CallModel {
        return CallModel(
            id = call.id.toLong(),
            callerId = call.callerId.toLong(),
            channelName = call.agoraChannel,
            agoraToken = call.agoraToken,
            receiverId = receiverId
        )
    }

}