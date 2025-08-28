package com.likelion.home.mypage.main_edit_info_screen

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.likelion.domain.home.model.UsersModel
import com.likelion.domain.mypage.model.UsersFullModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FakeMainEditInfoScreenViewModel(

    // usecase자리
): MainEditInfoScreenViewModelType {

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
    override fun playAudio(filePath: String) {
        try {
            val mediaPlayer = _uiState.value.mediaPlayer.apply {
                setDataSource(filePath)

                runPlayingTimer()
                prepare() // 파일을 불러올 준비를 합니다.
                start() // 재생 시작
            }
            // 재생이 끝나면 MediaPlayer 자원을 해제합니다.
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: Exception) {
            // 오류 처리
            e.printStackTrace()
        }
    }

    override fun stopAudio() {
        try {
            val mediaPlayer = _uiState.value.mediaPlayer.apply {
                pause()
            }
            // 재생이 끝나면 MediaPlayer 자원을 해제합니다.
            mediaPlayer.setOnCompletionListener {
                it.release()
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

    }

    fun fetchUsers() {

    }
}