package com.likelion.siso.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.component.bottomnavigation.BottomNavigationDestination
import com.likelion.ui.component.bottomnavigation.BottomNavigationItems
import com.likelion.ui.component.bottomnavigation.SisoBottomNavigation

@Composable
fun SisoApp(appState: SisoAppState = rememberSisoAppState()) {
    Scaffold(
        bottomBar = {
            if (appState.isBottomBarVisible()) {
                SisoBottomBar(
                    destinations = appState.bottomBarDestination,
                    onNavigationDestination = appState::navigationToBottomNavigationDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
