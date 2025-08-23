package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.chat.AlarmRoute
import com.likelion.home.chat.ChatRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToChat(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.ChatScreen.route, navOptions)

fun NavGraphBuilder.chatNavigation(
    navController: NavController,
    onNavigateUp: () -> Unit,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.ChatScreen.route
    ) {
        ChatRoute(
            onNavigateAlarm = {
                navController.navigate(NavigationRoute.ChatScreen.AlarmScreen.route) {
                    launchSingleTop = true
                }
            },
            actionSnackbar = action
        )
    }
    composable(NavigationRoute.ChatScreen.AlarmScreen.route) {
        AlarmRoute(
            onNavigateUp = { navController.popBackStack() }
        )
    }
}