package com.likelion.siso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.likelion.home.navigation.homeNavigation
import com.likelion.home.navigation.navigateToHome
import com.likelion.login.navigation.loginNavigation
import com.likelion.login.navigation.navigateToLogin
import com.likelion.navigation.NavigationRoute


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: SisoAppState,
    //startDestination: String = NavigationRoute.OnBoardingScreen.route
    startDestination: String = NavigationRoute.LoginScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        homeNavigation {
            appState.navController.navigateToHome()
        }
        loginNavigation {
            appState.navController.navigateToLogin()
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