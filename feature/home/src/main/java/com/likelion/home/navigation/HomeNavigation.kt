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
    // onNavigateToCaller 콜백이 userId와 otherUserId를 인자로 받도록 변경
    onNavigateToCaller: (otherUserId: Long) -> Unit,
    onNavigateToChat: (userId: Long, userNickName: String, chatRoomId: Long) -> Unit,
    action: () -> Unit,
) {
    composable(
        route = NavigationRoute.HomeScreen.route
    ) {
        // HomeRoute에 수정된 콜백 전달
        HomeRoute(
            actionSnackbar = action,
            onNavigateToCaller = { otherUser -> // HomeRoute에서 실제 값을 받아와서
                onNavigateToCaller(otherUser) // 콜백에 전달
            },
            onNavigateToChat = { userId, userNickName, chatRoomId ->
                onNavigateToChat(
                    userId, userNickName, chatRoomId
                )
            }
        )
    }
    composable(
        route = NavigationRoute.ChatScreen.ChatRoomScreen.route
    ) {

    }
}