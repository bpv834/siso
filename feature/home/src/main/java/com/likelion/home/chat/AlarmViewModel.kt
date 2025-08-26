package com.likelion.home.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(

) : ViewModel() {
    fun getCallHistory() {
        viewModelScope.launch {
        }
    }
}