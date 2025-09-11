package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likelion.navigation.NavigationRoute
import com.lion.call.call_for_receiver.CallForReceiverRouter
fun NavController.navigateToCallForReceiver(
    callerId: Long,
    channelName: String,
    navOptions: NavOptions? = null
) {
    // 라우트 경로에는 인자 없이 단순히 "receiver"만 사용
    currentBackStackEntry?.savedStateHandle?.set("callerId", callerId)
    currentBackStackEntry?.savedStateHandle?.set("channelName", channelName)
    navigate(NavigationRoute.CallForReceiverScreen.route, navOptions)
}

// NavGraphBuilder
fun NavGraphBuilder.receiverNavigation(
    action: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = NavigationRoute.CallForReceiverScreen.route // 인자 없이 간단히
    ) { backStackEntry ->
        // ViewModel이 SavedStateHandle로 값을 가져가므로 화면에서는 전달만
        CallForReceiverRouter(
            onNavigateUp = onNavigateUp
        )
    }
}
