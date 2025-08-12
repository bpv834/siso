package com.likelion.siso.navigation

import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.likelion.home.navigation.navigateToHome
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.component.bottomnavigation.BottomNavigationDestination


@Composable
fun rememberSisoAppState(navController: NavHostController = rememberNavController()): SisoAppState {
    return remember(navController) {
        SisoAppState(
            navController
        )
    }
}

@Stable
class SisoAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigationToBottomNavigationDestination(bottomNavigationDestination: BottomNavigationDestination) {
        val bottomNavigationOption =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        when (bottomNavigationDestination) {
            BottomNavigationDestination.Home -> navController.navigateToHome(bottomNavigationOption)
        }
    }

    @Composable
    fun isBottomBarVisible(): Boolean {
        return when (currentDestination?.route) {
            NavigationRoute.HomeScreen.route -> true
            NavigationRoute.LoginScreen.route -> false
            else -> false
        }
    }

    val bottomBarDestination: List<BottomNavigationDestination> = BottomNavigationDestination.values().toList()
//.toImmutableList()
    // okhttp3.internal.toImmutableList
}