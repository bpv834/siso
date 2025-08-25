package com.likelion.home.navigation.edit_Main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.android.material.snackbar.Snackbar
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import com.likelion.home.mypage.MyPageRoute
import com.likelion.navigation.NavigationRoute
import java.util.Objects

fun NavController.navigateToEditMain(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.MyPageScreen.MainEditScreen.route, navOptions)

fun NavGraphBuilder.editMainNavigation(
    navController: NavController,
    topLocationUseCase : TopLocationUseCase,
    bottomLocationUseCase : BottomLocationUseCase,
    actionSnackbar : () -> Unit
) {
    composable(
        route = NavigationRoute.MyPageScreen.MainEditScreen.route
    ) {
        EditMainRoute (
            topLocationUseCase = topLocationUseCase,
            bottomLocationUseCase = bottomLocationUseCase,
            actionSnackbar = actionSnackbar
        )
    }
}