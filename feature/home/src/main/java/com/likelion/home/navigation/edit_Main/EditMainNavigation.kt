package com.likelion.home.navigation.edit_Main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.android.material.snackbar.Snackbar
import com.likelion.home.mypage.MyPageRoute
import com.likelion.navigation.NavigationRoute

fun NavController.navigateToEditMain(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.MyPageScreen.MainEditScreen.route, navOptions)

fun NavGraphBuilder.editMainNavigation(
    navController: NavController,
    actionSnackbar : () -> Unit
) {
    composable(
        route = NavigationRoute.MyPageScreen.MainEditScreen.route
    ) {
        EditMainRoute (
            actionSnackbar = actionSnackbar
        )
    }
}