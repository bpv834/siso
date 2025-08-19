package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.chat.ChatRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToChat(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.ChatScreen.route, navOptions)

fun NavGraphBuilder.chatNavigation(action: () -> Unit) {
    composable(
        route = NavigationRoute.ChatScreen.route
    ) {
        ChatRoute(
            actionSnackbar = action
        )
    }
}