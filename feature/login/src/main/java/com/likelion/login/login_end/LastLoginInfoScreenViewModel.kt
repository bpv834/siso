package com.likelion.login.login_end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import com.likelion.domain.login.usecase.RegisterProfileToServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel @Inject constructor(
    val getTemporaryUserProfileUseCase: GetTemporaryUserProfileUseCase,
    val registerProfileToServerUseCase: RegisterProfileToServerUseCase,
    val getTokenAllUseCase: GetTokenAllUseCase

) : ViewModel() {


    fun onClick() {
        viewModelScope.launch {
            val user = getTemporaryUserProfileUseCase.execute()
            Timber.d("user ${user}")
            val result = getTokenAllUseCase().firstOrNull()
            Timber.d("onClick RefreshToken ${result?.refreshToken}")
            if (result?.refreshToken != null) {
                registerProfileToServerUseCase.execute(result.refreshToken, user)
            }
        }
    }

}