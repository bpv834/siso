package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.home_page.HomeRoute

import com.likelion.navigation.NavigationRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.route, navOptions)

fun NavGraphBuilder.homeNavigation(
    navController: NavController,
    onNavigateToCaller: () -> Unit, // 통화 화면으로 이동하는 콜백
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.HomeScreen.route
    ) {
        HomeRoute(
            actionSnackbar = action,
            onNavigateToCaller = onNavigateToCaller
        )
    }
}