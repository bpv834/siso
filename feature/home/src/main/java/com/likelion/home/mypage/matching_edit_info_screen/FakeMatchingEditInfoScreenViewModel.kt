package com.likelion.home.mypage.matching_edit_info_screen

import android.annotation.SuppressLint
import android.util.Log.d
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeMatchingEditInfoScreenViewModel(
    // usecase자리
): MatchingEditInfoScreenViewModelType {
    private val _receiverList = MutableStateFlow(mutableListOf<String>())

    override val receiverList = _receiverList.asStateFlow()
    fun fetch(list: List<String>){
        _receiverList.update {
            list.toMutableList()
        }
    }

    @SuppressLint("LogNotTimber")
    override fun addString(input: String){
        d("addString","$input")
        if (receiverList.value.contains(input)) {
            _receiverList.update { old->
                old.toMutableList().apply { remove(input) }
            }
            d("addString","_receiverList.value.contains(input)" +
                    "${_receiverList.value}")
        }else{
            _receiverList.update { old->
                old.toMutableList().apply { add(input) }
            }
            d("addString","not" +
                    "${_receiverList.value}")
        }
    }
}