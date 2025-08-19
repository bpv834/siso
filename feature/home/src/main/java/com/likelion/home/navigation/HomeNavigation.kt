package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.home.HomeRoute

import com.likelion.navigation.NavigationRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.route, navOptions)

fun NavGraphBuilder.homeNavigation(action: () -> Unit) {
    composable(
        route = NavigationRoute.HomeScreen.route
    ) {
        HomeRoute(
            actionSnackbar = action
        )
    }
}