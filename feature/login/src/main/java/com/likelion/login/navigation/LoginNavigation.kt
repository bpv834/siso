package com.likelion.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.login.InputRoute
import com.likelion.login.login_start.LoginRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.LoginScreen.route, navOptions)

fun NavGraphBuilder.loginNavigation(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.LoginScreen.route
    ) {

        LoginRoute(
            onInput = {
                navController.navigate(NavigationRoute.InputScreen.route) {
                    launchSingleTop = true
                }
            },
            onLogin = {
                navController.navigate(NavigationRoute.LoginScreen.route) {
                    popUpTo(NavigationRoute.LoginScreen.route) { inclusive = true }
                    launchSingleTop = true
                    restoreState = false
                }
            },
            onHome = onNavigateToHome,
            actionSnackbar = action
        )
    }
    composable(
        route = NavigationRoute.InputScreen.route
    ) {
        InputRoute(
            onNavigateUp = { navController.popBackStack() }
        )
    }
}