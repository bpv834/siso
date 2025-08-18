package com.likelion.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.login.login.LoginRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.LoginScreen.route, navOptions)

fun NavGraphBuilder.loginNavigation(
    navController: NavController,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.LoginScreen.route
    ) {
        LoginRoute(
            onLoggedIn = {
                //navController.navigateToInput()
                navController.navigate(NavigationRoute.InputScreen.route) {
                    popUpTo(NavigationRoute.LoginScreen.route) {
                        inclusive = true
                    }
                }
            },
            actionSnackbar = action
        )
    }
}