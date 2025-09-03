package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lion.call.call_for_caller.CallerRouter

fun NavController.navigateToCallForCaller(
    otherUserId: Long, // otherUserId 인자 추가
    navOptions: NavOptions? = null
) = navigate("caller/$otherUserId", navOptions) // 경로에 인자 포함

fun NavGraphBuilder.callerNavigation(
    action: () -> Unit,
    onNavigateUp : ()->Unit,
) {
    composable(
        // 라우트 경로에 인자 플레이스홀더를 명시합니다.
        route = "caller/{otherUserId}",
        arguments = listOf( // 인자들의 타입과 이름을 정의합니다.
            navArgument("otherUserId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        // NavBackStackEntry에서 인자를 가져옵니다.
        val otherUserId = backStackEntry.arguments?.getString("otherUserId")

        // 인자를 CallerRouter로 전달합니다.
        CallerRouter(
            actionSnackbar = action,
            otherUserId = otherUserId?.toLong()?:1L,
            onNavigateUp = onNavigateUp
        )
    }
}