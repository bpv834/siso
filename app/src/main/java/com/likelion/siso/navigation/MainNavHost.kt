package com.likelion.siso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.likelion.home.navigation.chatNavigation
import com.likelion.home.navigation.findNavigation
import com.likelion.home.navigation.homeNavigation
import com.likelion.home.navigation.myPageNavigation
import com.likelion.home.navigation.navigateToChat
import com.likelion.home.navigation.navigateToFind
import com.likelion.home.navigation.navigateToHome
import com.likelion.home.navigation.navigateToMyPage
import com.likelion.login.navigation.inputNavigation
import com.likelion.login.navigation.loginNavigation
import com.likelion.login.navigation.navigateToInput
import com.likelion.login.navigation.navigateToLogin
import com.likelion.navigation.NavigationRoute


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: SisoAppState,
    //startDestination: String = NavigationRoute.OnBoardingScreen.route
    //startDestination: String = NavigationRoute.LoginScreen.route
    startDestination: String = NavigationRoute.HomeScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        loginNavigation(
            navController = appState.navController
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
        homeNavigation {
            appState.navController.navigateToHome()
        }
        findNavigation {
            appState.navController.navigateToFind()
        }
        chatNavigation {
            appState.navController.navigateToChat()
        }
        myPageNavigation {
            appState.navController.navigateToMyPage()
        }

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