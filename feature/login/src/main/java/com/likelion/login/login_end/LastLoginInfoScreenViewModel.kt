package com.likelion.login.login_end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.RegisterProfileToServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel @Inject constructor(
    val getTemporaryUserProfileUseCase: GetTemporaryUserProfileUseCase,
    val registerProfileToServerUseCase : RegisterProfileToServerUseCase

) : ViewModel() {
    fun onClick(){
        viewModelScope.launch {
            val user = getTemporaryUserProfileUseCase.execute()
            Timber.d("user ${user}")

            registerProfileToServerUseCase.execute(user)
        }
    }

}