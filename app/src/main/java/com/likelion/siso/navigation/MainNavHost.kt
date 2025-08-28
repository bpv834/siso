package com.likelion.siso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.likelion.data.mypage.repository.APILocationRepositoryImpl
import com.likelion.data.mypage.repository.LocationRepositoryImpl
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.CurrentLocationSetUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import com.likelion.home.navigation.chatNavigation
import com.likelion.home.navigation.edit_Main.editMainNavigation
import com.likelion.home.navigation.edit_Main.settingMainNavigation
import com.likelion.home.navigation.homeNavigation
import com.likelion.home.navigation.myPageNavigation
import com.likelion.home.navigation.navigateToChat
import com.likelion.home.navigation.navigateToHome
import com.likelion.home.navigation.navigateToMyPage
import com.likelion.login.navigation.inputNavigation
import com.likelion.login.navigation.loginNavigation
import com.likelion.login.navigation.navigateToInput
import com.likelion.login.navigation.navigateToLogin
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.R
import com.lion.call.navigation.callerNavigation
import com.lion.call.navigation.navigateToCallForCaller


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: SisoAppState,
    startDestination: String = NavigationRoute.HomeScreen.route
//    startDestination: String = NavigationRoute.LoginScreen.route
) {
    val cotext = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        loginNavigation(
            navController = appState.navController,
            onNavigateToHome = {
                appState.navController.popBackStack(NavigationRoute.LoginScreen.route, inclusive = true)
                appState.navController.navigateToHome(
                    navOptions {
                        launchSingleTop = true
                    }
                )
            }
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
        homeNavigation (
            navController = appState.navController,
            // onNavigateToCaller 콜백에 userId와 otherUserId 인자를 추가하고,
            // navigateToCallForCaller 함수에 이 값들을 전달합니다.
            onNavigateToCaller = { userId, otherUserId ->
                appState.navController.navigateToCallForCaller(
                    userId = userId,
                    otherUserId = otherUserId,
                    navOptions = navOptions {
                        launchSingleTop = true
                    }
                )
            },
        ){
            appState.navController.navigateToHome()
        }
        chatNavigation(
            navController = appState.navController,
            onNavigateUp = {
                // 루트 NavController에서 popBackStack 시도.
                // 더 이상 pop할 수 없으면 Chat 탭으로 안전 복귀.
                if (!appState.navController.popBackStack()) {
                    appState.navController.navigateToChat(
                        navOptions { launchSingleTop = true }
                    )
                }
            }
        ) {
            appState.navController.navigateToChat()
        }
        myPageNavigation(
            navController = appState.navController,
            navigateToHome ={appState.navController.popBackStack(NavigationRoute.MyPageScreen.route, inclusive = true)}
        ) {

        }
        val inputStream = cotext.resources.openRawResource(R.raw.korea_regions_ordered)
        val jsonString  = inputStream.bufferedReader().use { it.readText() }
        val locationRepository = LocationRepositoryImpl()
        locationRepository.setJson(jsonString)

        val apiLocationRepository = APILocationRepositoryImpl()
        apiLocationRepository.setContext(cotext)
        editMainNavigation(
            navController = appState.navController,
            topLocationUseCase = TopLocationUseCase(locationRepository),
            bottomLocationUseCase = BottomLocationUseCase(locationRepository),
            currentLocationSetUseCase = CurrentLocationSetUseCase(apiLocationRepository)
        ){
            appState.navController.navigateToMyPage(
            navOptions {
                appState.navController.popBackStack(NavigationRoute.MyPageScreen.MainEditScreen.route,inclusive = true)
                launchSingleTop = true
            }
            )
        }

        settingMainNavigation(
            navController = appState.navController
        ) {
            appState.navController.navigateToMyPage(
                navOptions {
                    appState.navController.popBackStack(NavigationRoute.MyPageScreen.MainEditScreen.route,inclusive = true)
                    launchSingleTop = true
                }
            )
        }
        callerNavigation(
            action = { },
            onNavigateUp = {
                appState.navController.popBackStack()
            }
        )


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