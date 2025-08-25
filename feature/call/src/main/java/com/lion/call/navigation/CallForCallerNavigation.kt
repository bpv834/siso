package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likelion.navigation.NavigationRoute
import com.lion.call.call_for_caller.CallerRouter

fun NavController.navigateToCallForCaller(
    userId: String, // userId 인자 추가
    otherUserId: String, // otherUserId 인자 추가
    navOptions: NavOptions? = null
) = navigate("caller/$userId/$otherUserId", navOptions) // 경로에 인자 포함

fun NavGraphBuilder.callerNavigation(
    action: () -> Unit,
    onNavigateToCallForCaller: (userId: String, otherUserId: String) -> Unit
) {
    composable(
        // 라우트 경로에 인자 플레이스홀더를 명시합니다.
        route = "caller/{userId}/{otherUserId}",
        arguments = listOf( // 인자들의 타입과 이름을 정의합니다.
            navArgument("userId") { type = NavType.StringType },
            navArgument("otherUserId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        // NavBackStackEntry에서 인자를 가져옵니다.
        val userId = backStackEntry.arguments?.getString("userId")
        val otherUserId = backStackEntry.arguments?.getString("otherUserId")

        // 인자를 CallerRouter로 전달합니다.
        CallerRouter(
            actionSnackbar = action,
            userId = userId?:"테스트아이디",
            otherUserId = otherUserId?:"테스트유저아이디"
        )
    }
}