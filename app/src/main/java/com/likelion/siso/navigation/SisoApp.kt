package com.likelion.siso.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.likelion.ui.component.bottomnavigation.BottomNavigationDestination
import com.likelion.ui.component.bottomnavigation.BottomNavigationItems
import com.likelion.ui.component.bottomnavigation.SisoBottomNavigation
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SisoApp(appState: SisoAppState = rememberSisoAppState()) {
    Scaffold(
        // contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (appState.isBottomBarVisible()) {
                SisoBottomBar(
                    destinations = appState.bottomBarDestination,
                    onNavigationDestination = appState::navigationToBottomNavigationDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        content = { //_ ->
        innerPadding -> // <-- 여기가 변경되었습니다!
            Column(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
                 /*   .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                             WindowInsetsSides.Horizontal
                        )
                    )*/
                   // .windowInsetsPadding(WindowInsets.safeDrawing) // windowInsetsPadding은 시스템 UI(상단바, 하단 제스처 바)에만 적용하고,

            ) {
                MainNavHost(
                    appState = appState
                )
            }
        },
    )
}

@Composable
private fun SisoBottomBar(
    destinations: List<BottomNavigationDestination>,
    onNavigationDestination: (BottomNavigationDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    SisoBottomNavigation {
        destinations.forEach { destination ->
            val isSelected = currentDestination.isSelectedBottomNaviPage(destination)
            BottomNavigationItems(
                modifier = modifier,
                selected = isSelected,
                onClick = { onNavigationDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = null,
                    )
                },
                label = destination.routeName
            )
        }
    }
}

private fun NavDestination?.isSelectedBottomNaviPage(destination: BottomNavigationDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
