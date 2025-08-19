package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.mypage.MyPageRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.MyPageScreen.route, navOptions)

fun NavGraphBuilder.myPageNavigation(action: () -> Unit) {
    composable(
        route = NavigationRoute.MyPageScreen.route
    ) {
        MyPageRoute(
            actionSnackbar = action
        )
    }
}