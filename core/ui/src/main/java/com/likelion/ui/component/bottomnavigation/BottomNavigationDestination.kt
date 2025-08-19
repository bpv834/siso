package com.likelion.ui.component.bottomnavigation

import com.likelion.ui.R

enum class BottomNavigationDestination(
    val icon: Int,
    val routeName: Int
) {
    Home(
        icon = R.drawable.ic_home,
        routeName = R.string.home,
    ),
    Find(
        icon = R.drawable.ic_find,
        routeName = R.string.find,
    ),
    Chat(
        icon = R.drawable.ic_chat,
        routeName = R.string.chat
    ),
    MyPage(
        icon = R.drawable.ic_my_page,
        routeName = R.string.my_page
    )
}