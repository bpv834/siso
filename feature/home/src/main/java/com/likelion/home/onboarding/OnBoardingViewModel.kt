package com.likelion.home.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.domain.onboarding.usecase.ChangeOnBoardingSkipUseCase
import com.likelion.domain.onboarding.usecase.GetOnBoardingSkipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getOnBoardingSkipUseCase: GetOnBoardingSkipUseCase,
    private val changeOnBoardingSkipUseCase: ChangeOnBoardingSkipUseCase
) : ViewModel() {

    fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.ChangeOnBoardingSKip -> changeOnBoardingSkip(event.isSKip)
            OnBoardingEvent.GetOnBoardingSkip -> getOnBoardingSkip()
        }
    }

    private fun changeOnBoardingSkip(isSkip: Boolean) {
        viewModelScope.launch {
            changeOnBoardingSkipUseCase(isSkip)
        }
    }

    private fun getOnBoardingSkip() {
        viewModelScope.launch {
            val result = getOnBoardingSkipUseCase().firstOrNull()
            Timber.d("온보딩 상태: $result")

        }
    }
}