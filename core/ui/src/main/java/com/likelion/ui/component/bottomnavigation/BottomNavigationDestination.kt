package com.likelion.ui.component.bottomnavigation

import com.likelion.ui.R

enum class BottomNavigationDestination(
    val icon: Int,
    val routeName: Int
) {
    Home(
        icon = androidx.core.R.drawable.ic_call_answer,
        routeName = R.string.home,
    ),

}