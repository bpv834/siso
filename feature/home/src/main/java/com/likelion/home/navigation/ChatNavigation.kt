package com.likelion.home.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likelion.home.chat.AlarmRoute
import com.likelion.home.chat.ChatRoomRoute
import com.likelion.home.chat.ChatRoute
import com.likelion.navigation.NavigationRoute

// 상단 키 정의
private const val ARG_NICKNAME = "nickname"

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
            onNavigateChatRoom = { nickname: String ->
                val encoded = Uri.encode(nickname)
                navController.navigate(
                    NavigationRoute.ChatScreen.ChatRoomScreen.route + "?$ARG_NICKNAME=$encoded"
                ) {
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
    composable(
        route = NavigationRoute.ChatScreen.ChatRoomScreen.route + "?$ARG_NICKNAME={$ARG_NICKNAME}",
        arguments = listOf(
            navArgument(ARG_NICKNAME) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) { backStackEntry ->
        val nickname = backStackEntry.arguments?.getString(ARG_NICKNAME)
        ChatRoomRoute(
            nickname = nickname!!,
            onNavigateUp = { navController.popBackStack() },
        )
    }
}