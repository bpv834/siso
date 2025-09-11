package com.lion.call.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likelion.navigation.NavigationRoute
import com.lion.call.call_for_receiver.CallForReceiverRouter
// NavController 확장 함수
fun NavController.navigateToCallForReceiver(
    callerId: String,
    channelName: String,
    agoraToken: String
) {
    // 토큰만 Base64로 인코딩
    val encodedToken = android.util.Base64.encodeToString(
        agoraToken.toByteArray(), android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
    )

    navigate("receiver/$callerId/$channelName/$encodedToken")
}

// NavGraphBuilder
fun NavGraphBuilder.receiverNavigation(
    action : ()->Unit,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = NavigationRoute.CallForReceiverScreen.route,
        arguments = listOf(
            navArgument("callerId") { type = NavType.StringType },
            navArgument("channelName") { type = NavType.StringType },
            navArgument("encodedToken") { type = NavType.StringType }
        )
    ) { backStackEntry ->

        CallForReceiverRouter(
            onNavigateUp = onNavigateUp
        )
    }
}