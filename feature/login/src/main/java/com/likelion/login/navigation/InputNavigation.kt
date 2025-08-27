package com.likelion.login.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.login.InputRoute
import com.likelion.login.login_start.LoginScreenViewModel
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
        val viewModel = hiltViewModel<LoginScreenViewModel>()
        InputRoute(
            onNavigateInit = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
            },
            onExitRegister = {},
            onNavigateUp = { navController.navigateUp() },
            onNavigateToHome = onNavigateToHome,
            actionSnackbar = action
        )
    }
}