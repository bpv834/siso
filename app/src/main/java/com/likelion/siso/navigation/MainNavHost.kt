package com.likelion.siso.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.notification.FcmEvent
import com.example.notification.FcmEventBus
import com.likelion.domain.notification.model.Call
import com.likelion.home.navigation.chatNavigation
import com.likelion.home.navigation.edit_Main.editMainNavigation
import com.likelion.home.navigation.edit_Main.settingMainNavigation
import com.likelion.home.navigation.homeNavigation
import com.likelion.home.navigation.myPageNavigation
import com.likelion.home.navigation.navigateToChat
import com.likelion.home.navigation.navigateToChatRoom
import com.likelion.home.navigation.navigateToHome
import com.likelion.home.navigation.navigateToMyPage
import com.likelion.home.navigation.navigateToOnboarding
import com.likelion.home.navigation.onBoardingNavigation
import com.likelion.login.navigation.inputNavigation
import com.likelion.login.navigation.loginNavigation
import com.likelion.login.navigation.navigateToInput
import com.likelion.login.navigation.navigateToLogin
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.component.dialog.CallPopUpCard
import com.lion.call.navigation.callerNavigation
import com.lion.call.navigation.navigateToCallForCaller
import timber.log.Timber


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: SisoAppState,
//    startDestination: String = NavigationRoute.HomeScreen.route,
    startDestination: String = NavigationRoute.LoginScreen.route,
    viewModel: MainNavHostViewModel = hiltViewModel()
) {

    val cotext = LocalContext.current
    // 컴퍼저블이 열리면 이벤트 구독
    LaunchedEffect(Unit) {
        // event를 object로 만들어 싱글톤처럼 사용한다
        // 어디서든 접근가능하다.
        FcmEventBus.events.collect { event ->
            when (event) {
                // ui event 트리거 변경만 해준다
                // 전화 왔을때 이벤트
                is FcmEvent.Call -> viewModel.onFcmCallEvent(call = event.toCall())

                // 상대방이 거절했을 때 이벤트
                is FcmEvent.Reject -> {
                    // 발신자는 채널을 나간다
                    // 채널 나가고 아고라 거절됨 이벤트 발생시키는 이벤트
                    viewModel.onFcmRejectEvent()
                }

                is FcmEvent.Message -> {}
            }
        }
    }


    // 이벤트 상태를 로컬 Compose 상태로 변환
    var incomingCall by remember { mutableStateOf<Call?>(null) }

    val uiEvent = viewModel.uiEvent.collectAsState(initial = null)
    LaunchedEffect(uiEvent.value) {
        when (val event = uiEvent.value) {
            is UiEvent.IncomingCall -> {
                incomingCall = event.call
            }

            // 내가 거절했을 때
            is UiEvent.CallRejectedByMe -> {
                incomingCall = null // 팝업 내리기
            }

            // 전화 받았을때 화면전환 이벤트 일때
            is UiEvent.NavigateToCallScreen -> {
                incomingCall = null // 팝업 내리기
                // Navigation으로 CallForCallerScreen 이동
                appState.navController.navigateToCallForCaller(
                    otherUserId = event.call.callerId.toLong(),
                    navOptions = navOptions { launchSingleTop = true }
                )
            }

            is UiEvent.Error -> {
                Timber.e("에러 발생: ${event.message}")
            }

            // 상대방이 거절했을 때
            UiEvent.CallReject -> {}
            null -> {}
        }
    }

    // Compose 영역에서 조건부로 팝업 표시
    incomingCall?.let { call ->
        CallPopUpCard(
            call = call,
            onDismiss = { incomingCall = null },
            callAccept = { viewModel.acceptIncomingCall(call) },
            callDeny = { viewModel.rejectIncomingCall(call) }
        )
    }


    // 널체크 널이 아니면 전화 팝업 띄우기
    // 값 변화가 있을 때만 뜨지만, 이미 같은 값이 다시 들어오면 UI 반응이 없을 수 있음.
    /*  uiState.call?.let { state ->
          val currentCall = state  // 지역 변수에 복사
          CallPopUpCard(
              call = currentCall,
              onDismiss = { viewModel.clearCall() }
          )
      }*/

    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        loginNavigation(
            navController = appState.navController,
            onNavigateToHome = {
                appState.navController.popBackStack(
                    NavigationRoute.LoginScreen.route,
                    inclusive = true
                )
                appState.navController.navigateToHome(
                    navOptions {
                        launchSingleTop = true
                    }
                )
            },
            onNavigateToOnBoarding = {
                appState.navController.popBackStack(NavigationRoute.LoginScreen.route, inclusive = true)
                appState.navController.navigateToOnboarding(
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
                appState.navController.popBackStack(
                    NavigationRoute.LoginScreen.route,
                    inclusive = true
                )
                appState.navController.popBackStack(
                    NavigationRoute.InputScreen.route,
                    inclusive = true
                )
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
        onBoardingNavigation(
            navController = appState.navController,
        ) {
            appState.navController.navigateToOnboarding()
        }
        homeNavigation(
            navController = appState.navController,
            // onNavigateToCaller 콜백에 userId와 otherUserId 인자를 추가하고,
            // navigateToCallForCaller 함수에 이 값들을 전달합니다.
            onNavigateToCaller = { otherUserId ->
                appState.navController.navigateToCallForCaller(
                    otherUserId = otherUserId,
                    navOptions = navOptions {
                        launchSingleTop = true
                    }
                )
            },
            onNavigateToChat = { userId, userNickName, chatRoomId ->
                appState.navController.navigateToChatRoom(
                    userId = userId,
                    userNickName = userNickName,
                    chatRoomId = chatRoomId
                )
            },
        ) {
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
        ) {

        }
//        val inputStream = cotext.resources.openRawResource(R.raw.korea_regions_ordered)
//        val jsonString  = inputStream.bufferedReader().use { it.readText() }
//        val locationRepository = LocationRepositoryImpl()
//        locationRepository.setJson(jsonString)
        editMainNavigation(
            navController = appState.navController
        ) {
            appState.navController.navigateToMyPage(
                navOptions {
                    appState.navController.popBackStack(
                        NavigationRoute.MyPageScreen.MainEditScreen.route,
                        inclusive = true
                    )
                    launchSingleTop = true
                }
            )
        }

        settingMainNavigation(
            navController = appState.navController
        ) {
            appState.navController.navigateToMyPage(
                navOptions {
                    appState.navController.popBackStack(
                        NavigationRoute.MyPageScreen.MainEditScreen.route,
                        inclusive = true
                    )
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
    }
}