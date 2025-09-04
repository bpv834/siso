package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.likelion.home.onboarding.OnBoardingRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.OnBoardingScreen.route, navOptions)

fun NavGraphBuilder.onBoardingNavigation(
    navController: NavController,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.OnBoardingScreen.route
    ) {
        OnBoardingRoute(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo(NavigationRoute.OnBoardingScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            }
        )
    }
}