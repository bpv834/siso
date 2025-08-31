package com.likelion.login.login_end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.image.usecase.UploadImageUseCase
import com.likelion.domain.login.usecase.AddProfileUseCase
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.notification.model.FcmToken
import com.likelion.domain.notification.usecase.GetFcmTokenUseCase
import com.likelion.domain.notification.usecase.SendFcmTokenUseCase
import com.likelion.domain.voice.usecase.UploadVoiceSampleUseCase
import com.likelion.login.event.UiEvent
import com.likelion.login.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel @Inject constructor(
    private val getTemporaryUserProfileUseCase: GetTemporaryUserProfileUseCase,
    private val getTokenAllUseCase: GetTokenAllUseCase,
    private val addProfileUseCase: AddProfileUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadVoiceSampleUseCase: UploadVoiceSampleUseCase,
    private val sendFcmTokenUseCase: SendFcmTokenUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun onProfileUploadClicked() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.UploadProfile)
        }
    }

    init {
        handleEvents()
    }

    private fun handleEvents() {
        viewModelScope.launch {
            _uiEvent.collect { event ->
                when (event) {
                    is UiEvent.UploadProfile -> uploadProfile()
                    UiEvent.UploadFcmToken -> uploadFcmToken()
                }
            }
        }
    }

    private suspend fun uploadProfile() {
        _uiState.value = UiState.Loading

        try {
            val user = getTemporaryUserProfileUseCase.execute()
            val tokenResult = getTokenAllUseCase().firstOrNull()

            if (tokenResult?.refreshToken.isNullOrBlank()) {
                _uiState.value = UiState.Error("Refresh token not found")
                return
            }

            val accessToken = tokenResult.accessToken

            val workList = mutableListOf<Deferred<Result<Unit>>>()

            // 프로필 추가
            // Deferred : Kotlin 코루틴에서 비동기 작업의 결과를 나타내는 타입이에요. 쉽게 말하면 나중에 완료될 값을 담고 있는 상자
            val work1: Deferred<Result<Unit>> = viewModelScope.async(Dispatchers.IO) {
                // runCatching은 코드 블록에서 예외가 발생해도 앱이 터지지 않도록 감싸주는 함수
                runCatching {
                    addProfileUseCase.execute(
                        refreshToken = accessToken,
                        user = user
                    )
                }
            }
            workList.add(work1)

            /*       // 이미지 업로드
                   if (user.photoPaths.isNotEmpty()) {
                       val work2: Deferred<Result<Unit>> = viewModelScope.async(Dispatchers.IO) {
                           runCatching {
                               uploadImageUseCase.execute(
                                   eccessToken = accessToken,
                                   imgList = user.photoPaths
                               )
                           }
                       }
                       workList.add(work2)
                   }*/

       /*     // 음성 업로드
            if (user.voicePath.isNotBlank()) {
                val work3: Deferred<Result<Unit>> = viewModelScope.async(Dispatchers.IO) {
                    runCatching {
                        uploadVoiceSampleUseCase.execute(
                            path = user.voicePath,
                            refreshToken = accessToken
                        )
                    }
                }
                workList.add(work3)
            }*/

            // 병렬 수행
            val results = workList.awaitAll()

            // 실패가 하나라도 있으면 Error
            val failure = results.firstOrNull { it.isFailure }
            if (failure != null) {
                _uiState.value =
                    UiState.Error(failure.exceptionOrNull()?.message ?: "Unknown error")
            } else {
                _uiState.value = UiState.Success
                // 성공하면 fcm 토큰까지 서버로 보내기위한 이벤트 발생시키기
                _uiEvent.emit(UiEvent.UploadFcmToken)
            }

        } catch (e: Exception) {
            Timber.e(e, "프로필 등록 실패")
            _uiState.value = UiState.Error(e.message ?: "Unknown exception")
        }
    }

    // 저장소에 있는 fcm 토큰을 서버에 id와 매핑하기위해 보내는 메서드
    private suspend fun uploadFcmToken() {
        val fcmTokenString = getFcmTokenUseCase.invoke().firstOrNull()
        Timber.d("fcmToken = $fcmTokenString")
        val fcmToken = FcmToken(token = fcmTokenString!!)
        sendFcmTokenUseCase.invoke(fcmToken)
    }
}