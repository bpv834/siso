package com.likelion.login.login_end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.login.usecase.AddProfileUseCase
import com.likelion.domain.login.usecase.GetTemporaryUserProfileUseCase
import com.likelion.domain.login.usecase.GetTokenAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LastLoginInfoScreenViewModel @Inject constructor(
    val getTemporaryUserProfileUseCase: GetTemporaryUserProfileUseCase,
    val sendProfileToServerUseCase: AddProfileUseCase,
    val getTokenAllUseCase: GetTokenAllUseCase

) : ViewModel() {


    fun onClick() {
        viewModelScope.launch {
            try {
                val user = getTemporaryUserProfileUseCase.execute()
                Timber.d("user $user")
                val result = getTokenAllUseCase().firstOrNull()
                Timber.d("onClick access ${result?.refreshToken}")

                if (result?.refreshToken != null) {
                    sendProfileToServerUseCase.execute(result.refreshToken, user)
                    Timber.d("프로필 등록 성공")
                }
            } catch (e: Exception) {
                Timber.e(e, "프로필 등록 실패")
                // 필요시 LiveData/StateFlow로 UI에 오류 전달
            }
        }
    }

}