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
import com.likelion.home.chat.PartnerProfileScreenRoute
import com.likelion.navigation.NavigationRoute

// 상단 키 정의
private const val ARG_NICKNAME = "nickname"
private const val ARG_CHATROOM_ID = "chatRoomId"
private const val ARG_PARTNER_ID = "partnerId"

fun NavController.navigateToChat(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.ChatScreen.route, navOptions)

fun NavController.navigateToChatRoom(
    // 채팅방 id api넣어야함
    userId: Long,
    userNickName: String,
    chatRoomId: Long,
    navOptions: NavOptions? = null
) {
    val encodedNick = Uri.encode(userNickName)
    return navigate(
        NavigationRoute.ChatScreen.ChatRoomScreen.route +
                "?$ARG_NICKNAME=$encodedNick&$ARG_CHATROOM_ID=$chatRoomId",
        navOptions
    )
}

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
            onNavigateChatRoom = { nickname: String, chatRoomId: Long ->
                val encoded = Uri.encode(nickname)
                navController.navigate(
                    NavigationRoute.ChatScreen.ChatRoomScreen.route +
                            "?$ARG_NICKNAME=$encoded&$ARG_CHATROOM_ID=$chatRoomId"
                ) {
                    launchSingleTop = true
                }
            },
            onNavigatePartner = {
                navController.navigate(NavigationRoute.ChatScreen.PartnerProfileScreen.route) {
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
        route = NavigationRoute.ChatScreen.PartnerProfileScreen.route +
                "?$ARG_PARTNER_ID={$ARG_PARTNER_ID}",
        arguments = listOf(
            navArgument(ARG_PARTNER_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        PartnerProfileScreenRoute(
            onNavigateUp = { navController.popBackStack() }
        )
    }
    composable(
        route = NavigationRoute.ChatScreen.ChatRoomScreen.route +
                "?$ARG_NICKNAME={$ARG_NICKNAME}&$ARG_CHATROOM_ID={$ARG_CHATROOM_ID}",
        arguments = listOf(
            navArgument(ARG_NICKNAME) {
                type = NavType.StringType
                defaultValue = ""           // 필요 시 nullable = false 로 변경 가능
            },
            navArgument(ARG_CHATROOM_ID) {
                type = NavType.LongType
                defaultValue = -1L          // nullable=false로 쓰려면 default 제거하고 반드시 전달
            }
        )
    ) { backStackEntry ->
        val nickname = backStackEntry.arguments?.getString(ARG_NICKNAME).orEmpty()
        val chatRoomId = backStackEntry.arguments?.getLong(ARG_CHATROOM_ID) ?: -1L

        ChatRoomRoute(
            nickname = nickname,
            chatRoomId = chatRoomId,
            onNavigateUp = { navController.popBackStack() },
        )
    }

}