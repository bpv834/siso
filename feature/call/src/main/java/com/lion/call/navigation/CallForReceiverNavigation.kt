package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lion.call.call_for_receiver.CallForReceiverRouter

fun NavController.navigateToCallForReceiver(
    receiverId: Long, // otherUserId 인자 추가
    navOptions: NavOptions? = null
) = navigate("receiver/$receiverId", navOptions) // 경로에 인자 포함

fun NavGraphBuilder.receiverNavigation(
    action: () -> Unit,
    onNavigateUp : ()->Unit,
) {
    composable(
        // 라우트 경로에 인자 플레이스홀더를 명시합니다.
        route = "receiver/{receiverId}",
        arguments = listOf( // 인자들의 타입과 이름을 정의합니다.
            navArgument("receiverId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        // NavBackStackEntry에서 인자를 가져옵니다.
        val otherUserId = backStackEntry.arguments?.getString("receiverId")

        // 인자를 CallerRouter로 전달합니다.
        CallForReceiverRouter(
            otherUserId = otherUserId?.toLong()?:1L,
            onNavigateUp = onNavigateUp,

        )
    }
}