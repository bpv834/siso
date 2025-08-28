package com.likelion.home.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.chat.usecase.GetCallHistoryUseCase
import com.likelion.domain.chat.usecase.GetChatHistoryUseCase
import com.likelion.domain.chat.usecase.SendMyChatUseCase
import com.likelion.domain.chat.usecase.GetPartnerChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getCallHistoryUseCase: GetCallHistoryUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendChatUseCase: SendMyChatUseCase,
    private val getPartnerChatUseCase: GetPartnerChatUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        handleEvent(ChatEvent.LoadCallHistory)
    }

    fun handleEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.LoadCallHistory -> getCallHistory()
            is ChatEvent.LoadChatHistory -> getChatHistory()
            is ChatEvent.RemoveCallHistory -> removeCallHistory()
            is ChatEvent.RemoveChatHistory -> removeChatHistory()
            is ChatEvent.SendChat -> sendChat(event.chat)
        }
    }

//    fun callReduce(state: ChatUiState, event: ChatEvent): ChatUiState {
//        return when (event) {
//            is ChatEvent.CallHistoryImageLoaded -> {
//                val updated = state.callHistory.map {
//                    if (it.id == event.id) it.copy(isCallImageLoaded = true) else it
//                }
//                val allLoaded = updated.all { it.isCallImageLoaded }
//                state.copy(callHistory = updated, isCallHistoryLoading = !allLoaded)
//            }
//            else -> state
//        }
//    }
//
//
//    fun chatReduce(state: ChatUiState, event: ChatEvent): ChatUiState {
//        return when (event) {
//            is ChatEvent.ChatHistoryImageLoaded -> {
//                val updated = state.chatHistory.map {
//                    if (it.id == event.id) it.copy(isChatImageLoaded = true) else it
//                }
//                val allLoaded = updated.all { it.isChatImageLoaded }
//                state.copy(chatHistory = updated, isChatHistoryLoading = !allLoaded)
//            }
//            else -> state
//        }
//    }

    private fun getPartnerChat() {
        viewModelScope.launch {
            getPartnerChatUseCase()
                .onStart { Timber.d("파트너 채팅 로딩 시작") }
                .catch { e -> Timber.e("에러: $e") }
                .collect { chat ->
                    chat.forEachIndexed { idx, item ->
                        delay(800L)
                        _uiState.update { state ->
                            state.copy(
                                partnerChatList = state.partnerChatList + item
                            )
                        }
                    }
                }

        }
    }

    fun sendChat(chat: String) {
        viewModelScope.launch {
            Timber.d("메세지 보냄: $chat")
            val msg = sendChatUseCase(chat)
            _uiState.update {
                it.copy(
                    myChat = msg
                )
            }
            getPartnerChat()
        }
    }

    fun removeCallHistory() {

    }

    fun removeChatHistory() {

    }

    fun getChatHistory() {
        //	•	이미 isChatHistoryLoading == true → 지금 로딩 중이라면 새로 시작하지 말고 그냥 끝내라
        //	•	혹은 chatHistory 리스트가 비어있지 않음 → 이미 데이터가 들어있다면 또 불러올 필요 없으니 그냥 끝내라
        if (_uiState.value.isChatHistoryLoading || _uiState.value.chatHistory.isNotEmpty()) return
        viewModelScope.launch {
            getChatHistoryUseCase()
                .onStart {
                    _uiState.update {
                        it.copy(isChatHistoryLoading = true, error = null)
                    }
                    Timber.d("[getChatHistory] onStart: set loading=true")
                }.catch { e ->
                    _uiState.update {
                        it.copy(isChatHistoryLoading = false, error = e.message ?: "error")
                    }
                }.collect { list ->
                    _uiState.update {
                        it.copy(
                            isChatHistoryLoading = false,
                            chatHistory = list,
                            error = null
                        )
                    }
                    Timber.d("[getChatHistory] collected = %d items", list.size)
                }
        }
    }
/*    fun getChatHistory() {
        //	•	이미 isChatHistoryLoading == true → 지금 로딩 중이라면 새로 시작하지 말고 그냥 끝내라
        //	•	혹은 chatHistory 리스트가 비어있지 않음 → 이미 데이터가 들어있다면 또 불러올 필요 없으니 그냥 끝내라
        if (_uiState.value.isChatHistoryLoading || _uiState.value.chatHistory.isNotEmpty()) return
        viewModelScope.launch {
            getChatHistoryUseCase()
                .onStart {
                    _uiState.update {
                        it.copy(isChatHistoryLoading = true, error = null)
                    }
                    Timber.d("[getChatHistory] onStart: set loading=true")
                }.catch { e ->
                    _uiState.update {
                        it.copy(isChatHistoryLoading = false, error = e.message ?: "error")
                    }
                }.collect { list ->
                    _uiState.update { prev ->
                        val stillLoading = list.any { !it.isChatImageLoaded }
                        prev.copy(
                            isChatHistoryLoading = stillLoading,
                            chatHistory = list,
                            error = null
                        )
                    }
                    Timber.d("[getChatHistory] collected = %d items (images loaded? %b)", list.size, list.all { it.isChatImageLoaded })
                }
        }
    }*/

    fun getCallHistory() {
        viewModelScope.launch {
            getCallHistoryUseCase()
                .onStart {
                    _uiState.update {
                        it.copy(isCallHistoryLoading = true, error = null)
                    }
                }.catch { e ->
                    _uiState.update {
                        it.copy(isCallHistoryLoading = false, error = e.message ?: " error")
                    }
                }.collect { list ->
                    _uiState.update {
                        it.copy(
                            isCallHistoryLoading = false,
                            callHistory = list,
                            error = null
                        )
                    }
                }
        }
    }
}