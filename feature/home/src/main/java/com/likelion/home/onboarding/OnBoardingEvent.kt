package com.likelion.home.onboarding

sealed class OnBoardingEvent {
    data class ChangeOnBoardingSKip(val isSKip: Boolean) : OnBoardingEvent()
    data object GetOnBoardingSkip : OnBoardingEvent()
}