package com.likelion.siso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.likelion.home.navigation.chatNavigation
import com.likelion.home.navigation.homeNavigation
import com.likelion.home.navigation.myPageNavigation
import com.likelion.home.navigation.navigateToChat
import com.likelion.home.navigation.navigateToHome
import com.likelion.home.navigation.navigateToMyPage
import com.likelion.login.navigation.inputNavigation
import com.likelion.login.navigation.loginNavigation
import com.likelion.login.navigation.navigateToInput
import com.likelion.login.navigation.navigateToLogin
import com.likelion.navigation.NavigationRoute
import com.lion.call.navigation.callerNavigation
import com.lion.call.navigation.navigateToCallForCaller


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: SisoAppState,
    startDestination: String = NavigationRoute.HomeScreen.route
    //startDestination: String = NavigationRoute.LoginScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        loginNavigation(
            navController = appState.navController,
            onNavigateToHome = {
                appState.navController.popBackStack(NavigationRoute.LoginScreen.route, inclusive = true)
                appState.navController.navigateToHome(
                    navOptions {
                        launchSingleTop = true
                    }
                )
            }
        ) {
            appState.navController.navigateToLogin()
        }
        inputNavigation(
            navController = appState.navController,
            onNavigateToHome = {
                appState.navController.popBackStack(NavigationRoute.LoginScreen.route, inclusive = true)
                appState.navController.popBackStack(NavigationRoute.InputScreen.route, inclusive = true)
                appState.navController.navigateToHome(
                    navOptions {
                        launchSingleTop = true
                    }
                )
//                appState.navController.navigateToHome(
//                    navOptions {
//                        popUpTo(NavigationRoute.InputScreen.route) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                )
            }
        ) {
            appState.navController.navigateToInput()
        }
        homeNavigation (
            navController = appState.navController,
            // onNavigateToCaller 콜백에 userId와 otherUserId 인자를 추가하고,
            // navigateToCallForCaller 함수에 이 값들을 전달합니다.
            onNavigateToCaller = { userId, otherUserId ->
                appState.navController.navigateToCallForCaller(
                    userId = userId,
                    otherUserId = otherUserId,
                    navOptions = navOptions {
                        launchSingleTop = true
                    }
                )
            },
        ){
            appState.navController.navigateToHome()
        }
        chatNavigation(
            navController = appState.navController,
            onNavigateUp = {
                // 루트 NavController에서 popBackStack 시도.
                // 더 이상 pop할 수 없으면 Chat 탭으로 안전 복귀.
                if (!appState.navController.popBackStack()) {
                    appState.navController.navigateToChat(
                        navOptions { launchSingleTop = true }
                    )
                }
            }
        ) {
            appState.navController.navigateToChat()
        }
        myPageNavigation {
            appState.navController.navigateToMyPage()
        }
        callerNavigation(
            action = { },
            onNavigateUp = {
                appState.navController.popBackStack()
            }
        )


        /*
        *
        * onBoardingNavigation(
            navigateToHome = { appState.navController.navigateToHome() },
        )
        homeNavigation {
            appState.navController.navigateToSaveLink()
        }
        storageNavigation(appState.navController)
        storageDetailNavigation(appState.navController)
        saveLinkNavigation(appState.navController)
        * */
    }
}