package com.likelion.login.login_agree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.login.usecase.ClearTemporaryUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgreeToTermsScreenViewModel @Inject constructor(
    // usecase자리
    private val clearProfile: ClearTemporaryUserProfileUseCase
): ViewModel(), AgreeToTermsScreenViewModelType {
    init {
        viewModelScope.launch {
            clearProfile.execute()
        }
    }
    private val _agreeContinueBoolean = MutableStateFlow(false)
    override val agreeContinueBoolean : StateFlow<Boolean> get() = _agreeContinueBoolean.asStateFlow()
    private val _isShowBottomSheet = MutableStateFlow(false)

    override fun agreeContinueBooleanUpdate(agreeStates: Boolean) = _agreeContinueBoolean.update {
        agreeStates
    }
}