package com.likelion.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.login.InputRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToInput(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.InputScreen.route, navOptions)

fun NavGraphBuilder.inputNavigation(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.InputScreen.route,
    ) {
        InputRoute(
            onNavigateUp = { navController.navigateUp() },
            onNavigateToHome = onNavigateToHome,
            actionSnackbar = action
        )
    }
}