package com.likelion.home.mypage.mbti_edit_info_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MBTIEditInfoScreenViewModel @Inject constructor (
    //usecase자리
): ViewModel(), MBTIEditInfoScreenViewModelType {
    private var _receiver = MutableStateFlow("")
    override val nothing :String = "|||"
    override fun setReceiver(receiver: String) {
        _receiver.update {
            receiver.ifBlank { nothing }
        }
    }

    override val receiver: StateFlow<String> = _receiver.asStateFlow()
}