package com.likelion.home.mypage.main_edit_info_screen

import android.R.attr.duration
import android.R.id.input
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.mypage.model.UsersFullModel
import com.likelion.domain.mypage.usecase.UsersFullUseCase
import com.likelion.home.mypage.getBitmapFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.get

@HiltViewModel
class MainEditInfoScreenViewModel @Inject constructor (
    //usecase자리
    private val userFullUseCase: UsersFullUseCase,
): ViewModel(), MainEditInfoScreenViewModelType {

    private var _uiState = MutableStateFlow(EditUiState())
    override val uiState = _uiState.asStateFlow()

    override fun fistContinueBooleanUpdate() = _uiState.update {
        val newUi = it.copy(
            firstContinueBoolean = it.fistContinueBooleanUpdate()
        )
        d("boolean","ui $newUi")
        newUi
    }

    override fun nameUpdate(input: String) = _uiState.update {
        val tempUi = it.copy(editUsersModel = it.editUsersModel.copy(nickname = input))
        tempUi.copy(firstContinueBoolean = it.fistContinueBooleanUpdate())
    }

    override fun myRadioButtonsUpdate(sex: String) = _uiState.update {
        val male = it.myRadioButtons[0].first
        val feMale = it.myRadioButtons[1].first
            it.copy(
                myRadioButtons = listOf(
                        male to (male == sex),
                        feMale to (feMale == sex)
                    )
            )
        }

    override fun pairRadioButtonsUpdate(pair: String) = _uiState.update {
        val other = it.pairRadioButtons[0].first
        val equil = it.pairRadioButtons[1].first
        val nothing = it.pairRadioButtons[2].first
        it.copy(
            pairRadioButtons = listOf(
                other to (other == pair),
                equil to (equil == pair),
                nothing to (nothing == pair)
            )
        )
    }

    @SuppressLint("DefaultLocale")
    override fun playAudio(context: Context) {
        try {
            d("audio","playAudio")
            val mediaPlayer = _uiState.value.mediaPlayer
            mediaPlayer.apply {
                reset() // 중요! Idle 상태로 돌려놓기
                setDataSource(context, Uri.parse(uiState.value.voicePath))
                setOnPreparedListener {
                    start() // 재생 시작
                    _uiState.update { it.copy(playState = true) }
                    runPlayingTimer()
                }
                // 재생이 끝나면 MediaPlayer 자원을 해제합니다.
                setOnCompletionListener {
                    _uiState.update { it.copy(playState = false,playTime = mediaPlayer.duration) }
                    uiState.value.playJob?.cancel()
                }

                prepareAsync()// 파일을 불러올 준비를 합니다.
            }
        } catch (e: Exception) {
            // 오류 처리
            e.printStackTrace()
        }
    }

    override fun stopAudio() {
        try {
            val mediaPlayer = _uiState.value.mediaPlayer
            if (mediaPlayer.isPlaying) mediaPlayer.pause()
            uiState.value.playJob?.wait()
            _uiState.update {
                it.copy(playState = false)
            }
        } catch (e: Exception) {
            // 오류 처리
            e.printStackTrace()
        }
    }

    // 타이머 시작 메서드
    override fun runPlayingTimer() {
        // 기존 Job이 있다면 취소
        uiState.value.playJob?.cancel()
        val job = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val mediaPlayer = uiState.value.mediaPlayer
                val duration = mediaPlayer.duration // 총 재생 길이(ms)
                val currentPos = try {
                    mediaPlayer.currentPosition
                } catch (e: IllegalStateException) {
                    break // 이미 release 됐으면 안전 종료
                }
                _uiState.update { it.copy(playTime = duration - currentPos) }
            }
        }
        _uiState.update {
            it.copy(playJob = job)
        }
    }

    @SuppressLint("LogNotTimber")
    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val newModel = userFullUseCase(1)
                _uiState.update {
                    val male = it.myRadioButtons[0].first
                    val feMale = it.myRadioButtons[1].first
                    val other = it.pairRadioButtons[0].first
                    val equil = it.pairRadioButtons[1].first
                    val nothing = it.pairRadioButtons[2].first
                    it.copy(receiverUsersModel = newModel,/* get user */
                        myRadioButtons = listOf(
                            male to (male == newModel.sex),
                            feMale to (feMale == newModel.sex)
                        ),
                        pairRadioButtons = listOf(
                            other to (other == newModel.preferenceSex),
                            equil to (equil == newModel.preferenceSex),
                            nothing to (nothing == newModel.preferenceSex)
                        ),
                        voicePath = newModel.voiceUrl,
                    )
                }
                // viewModel Io 따로 로딩
                _uiState.update {
                    it.copy(playTime = getAudioDurationFromUrl(newModel.voiceUrl) ?: 0)
                }
                d("newModel", "nickname ${newModel.nickname}")
                d("newModel", "age ${newModel.age}")
                d("newModel", "uiState nickname ${uiState.value.editUsersModel.nickname}")
                d("newModel", "uiState age ${uiState.value.editUsersModel.age}")

            } catch (e: Exception) {
                Log.e("API_ERROR", e.message.toString())
            }
        }
    }

    override fun updateUsers(usersModel: UsersFullModel) {
        _uiState.update {
            it.copy(editUsersModel = usersModel)
        }
    }

    fun completeUsers(usersModel: UsersFullModel) {
        usersModel.nickname != uiState.value.editUsersModel.nickname
        if (usersModel.nickname.isBlank()) return
    }


    suspend fun getAudioDurationFromUrl(url: String): Int? {
        return withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(url, HashMap()) // 네트워크 URL 가능
                val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                retriever.release()
                durationStr?.toInt() // 밀리초 단위
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}