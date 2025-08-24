package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.navigation.NavigationRoute
import com.lion.call.call_for_caller.CallerRouter

fun NavController.navigateToCallForCaller(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.CallForCallerScreen.route, navOptions)

fun NavGraphBuilder.callerNavigation(action: () -> Unit) {
    composable(
        route = NavigationRoute.CallForCallerScreen.route
    ) {
        CallerRouter(
            actionSnackbar = action
        )
    }
}