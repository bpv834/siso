package com.likelion.login.LastLoginInfoPage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel  @Inject constructor(
    // usecase
) : ViewModel(), LastLoginInfoScreenViewModelType{
    override fun onClickNextButton() {
        TODO("Not yet implemented")
    }

}