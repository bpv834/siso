package com.likelion.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.likelion.home.mypage.MyPageRoute
import com.likelion.home.navigation.edit_Main.navigateToEditMain
import com.likelion.home.navigation.edit_Main.navigateToSettingMain
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.MyPageScreen.route, navOptions)

fun NavGraphBuilder.myPageNavigation(
    navController: NavController,
    navigateToHome: () -> Unit,
    action: () -> Unit
) {
    composable(
        route = NavigationRoute.MyPageScreen.route
    ) {
        MyPageRoute(
            navigateToHome = navigateToHome,
            mainEdit = {
                navController.navigateToEditMain()
            },
            setting = {
                navController.navigateToSettingMain()
            },
            actionSnackbar = action,
        )
    }
}