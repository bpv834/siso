package com.likelion.home.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.chat.usecase.GetCallHistoryUseCase
import com.likelion.domain.chat.usecase.GetChatHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getChatHistoryUseCase: GetChatHistoryUseCase
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