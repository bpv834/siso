package com.likelion.home.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.mypage.usecase.UsersFullUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FakeMyPageViewModel @Inject constructor(

):MyPageViewModelType{
    init {
        getUsers()
    }
    private val _uiState = MutableStateFlow(MyPageUiState())
    override val uiState : StateFlow<MyPageUiState> = _uiState.asStateFlow()
    fun getUsers(){
        _uiState.update {
            it.copy(
                userImages = "http://www.civicnews.com/news/photo/201811/19147_26513_953.png",
                nickname = "코딩러",
                age = "65",
                location = "America",
                progressValue = 1F,
            )
        }
    }
}