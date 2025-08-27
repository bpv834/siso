package com.likelion.home.navigation.edit_Main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToSettingMain(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.MyPageScreen.SettingScreen.route, navOptions)

fun NavGraphBuilder.settingMainNavigation(
    navController: NavController,
    actionSnackbar : () -> Unit
) {
    composable(
        route = NavigationRoute.MyPageScreen.SettingScreen.route
    ) {
        SettingMainRoute (
            actionSnackbar = actionSnackbar
        )
    }
}