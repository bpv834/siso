package com.likelion.home.mypage.main_edit_info_screen

import android.media.MediaPlayer
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainEditInfoScreenViewModel @Inject constructor (
    //usecase자리
    private val userFullUseCase: UsersFullUseCase,
): ViewModel(), MainEditInfoScreenViewModelType {

    private val mediaPlayer = MediaPlayer()
    private val _potoState = MutableStateFlow("")
    override val potoState : StateFlow<String> get() = _potoState.asStateFlow() // 사진 주소
    private val _voiceState = MutableStateFlow("")
    override val voiceState : StateFlow<String> get() = _voiceState.asStateFlow() // 사진 주소
    private val _nameState = MutableStateFlow("")
    override val nameState : StateFlow<String> get() = _nameState.asStateFlow() // 이름
    private val _ageState = MutableStateFlow("")
    override val ageState: StateFlow<String> get() = _ageState.asStateFlow() // 나이
    private val _introduceState = MutableStateFlow("")
    override val introduceState: StateFlow<String> get() = _introduceState.asStateFlow()
    private val _myRadioButtons = MutableStateFlow(                   // 성별
        mutableStateListOf(
            Pair(first = "여성", second = false),
            Pair(first = "남성", second = false),
        )
    )
    override val myRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>> get() = _myRadioButtons.asStateFlow()
    private val _pairRadioButtons = MutableStateFlow(                // 매칭 상대
        mutableStateListOf(
            Pair(first = "이성", second = false),
            Pair(first = "동성", second = false),
            Pair(first = "상관없음", second = false),
        )
    )
    override val pairRadioButtons : StateFlow<MutableList<Pair<String, Boolean>>> get() = _pairRadioButtons.asStateFlow()
    private val _firstContinueBoolean = MutableStateFlow(false)
    override val fistContinueBoolean : StateFlow<Boolean> get() = _firstContinueBoolean.asStateFlow()
    val _receiverUsersModel =MutableStateFlow<UsersFullModel?>(null)
    override val receiverUsersModel: StateFlow<UsersFullModel?> get() = _receiverUsersModel.asStateFlow()
    val _usersModel =MutableStateFlow<UsersFullModel?>(null)
    override val usersModel: StateFlow<UsersFullModel?> get() = _receiverUsersModel.asStateFlow()

    override fun fistContinueBooleanUpdate() = _firstContinueBoolean.update {
        val textBoolean = nameState.value.isNotBlank() && ageState.value.isNotBlank()
        d("boolean","text $ageState")
        d("boolean","text $nameState")
        d("boolean","text $textBoolean")

        val tempBoolean = myRadioButtons.value.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","tempBoolean $pair")
            boolean
        }.second && textBoolean
        val continueBoolean = pairRadioButtons.value.reduce { acc, pair ->
            val boolean = acc.copy(second = pair.second || acc.second)
            d("boolean","continueBoolean $pair")
            boolean
        }.second && tempBoolean
        d("boolean","continueBoolean $continueBoolean")
        continueBoolean
    }

    override fun nameUpdate(input: String) {
        _nameState.update { input }
        fistContinueBooleanUpdate()
    }

    override fun playAudio(filePath: String) {
        try {
            val mediaPlayer = mediaPlayer.apply {
                setDataSource(filePath)
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
            val mediaPlayer = mediaPlayer.apply {
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

     fun fetchUsers() {
        viewModelScope.launch {
            try {
                _receiverUsersModel.update {
                    userFullUseCase(1)/* get user */
                }
                d("userImage","${receiverUsersModel.value}")
                _potoState.update { receiverUsersModel.value?.userImages?.first() ?: "" } // 사진
                _voiceState.update { receiverUsersModel.value?.voiceUrl ?: "" } // 음성
                _nameState.update{ receiverUsersModel.value?.nickname ?: "" } // 이름
                _ageState.update { receiverUsersModel.value?.age.toString() } // 나이
                _introduceState.update { receiverUsersModel.value?.introduce ?: "" }

                val male = myRadioButtons.value[0].first
                val feMale = myRadioButtons.value[1].first
                _myRadioButtons.value[0] = male to (male == receiverUsersModel.value?.sex)
                _myRadioButtons.value[1] = feMale to (feMale == receiverUsersModel.value?.sex)

                val other = pairRadioButtons.value[0].first
                val equil = pairRadioButtons.value[1].first
                val nothing = pairRadioButtons.value[2].first

                pairRadioButtons.value[0] = male to (other == receiverUsersModel.value?.preferenceSex)
                pairRadioButtons.value[1] = feMale to (equil == receiverUsersModel.value?.preferenceSex)
                pairRadioButtons.value[2] = nothing to (nothing == receiverUsersModel.value?.preferenceSex)

            } catch (e: Exception) {
                Log.e("API_ERROR", e.message.toString())
            }
        }
    }
}