package com.likelion.home.navigation.edit_Main

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.CurrentLocationSetUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.AdditionalInfoAlcoholScreen
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.FakeAdditionalInfoAlcoholScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.AdditionalInfoReligionScreen
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.FakeAdditionalInfoReligionScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.AdditionalInfoSmokingScreen
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.FakeAdditionalInfoSmokingScreenViewModel
import com.likelion.home.mypage.interest_edit_info_screen.FakeInterestEditInfoScreenViewModel
import com.likelion.home.mypage.interest_edit_info_screen.InterestEditInfoScreen
import com.likelion.home.mypage.location_edit_info_screen.LocationEditInfoScreen
import com.likelion.home.mypage.location_edit_info_screen.LocationEditInfoScreenViewModel
import com.likelion.home.mypage.main_edit_info_screen.FakeMainEditInfoScreenViewModel
import com.likelion.home.mypage.main_edit_info_screen.MainEditInfoScreen
import com.likelion.home.mypage.matching_edit_info_screen.FakeMatchingEditInfoScreenViewModel
import com.likelion.home.mypage.matching_edit_info_screen.MatchingEditInfoScreen
import com.likelion.home.mypage.mbti_edit_info_screen.FakeMBTIEditInfoScreenViewModel
import com.likelion.home.mypage.mbti_edit_info_screen.MBTIEditInfoScreen
import com.likelion.home.mypage.poto_edit_info_screen.PotoEditInfoScreen
import com.likelion.home.mypage.poto_edit_info_screen.PotoEditInfoScreenViewModel
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme

@Composable
fun EditMainRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    topLocationUseCase : TopLocationUseCase,
    bottomLocationUseCase : BottomLocationUseCase,
    currentLocationSetUseCase : CurrentLocationSetUseCase,
    actionSnackbar: () -> Unit = {}
) {
    SisoTheme {
        EditMain(
            topLocationUseCase = topLocationUseCase,
            bottomLocationUseCase = bottomLocationUseCase,
            currentLocationSetUseCase = currentLocationSetUseCase,
            navigateToMyPage = actionSnackbar
        )
    }

}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMain (
    topLocationUseCase : TopLocationUseCase,
    bottomLocationUseCase : BottomLocationUseCase,
    currentLocationSetUseCase : CurrentLocationSetUseCase,
    navigateToMyPage : () -> Unit = {},
) {
    val title = stringResource(com.likelion.home.R.string.main_edit)
    val navController = rememberNavController()
    var appBarTitle by remember { mutableStateOf(title) }
    val start = NavigationRoute.MyPageScreen.MainEditScreen.route
    val onlyTopInnerPadding = listOf(
        NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route,
        NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route
    )
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = SisoColorTokens.White,
                        titleContentColor = SisoColorTokens.Gray90
                    ),
                    title = {
                        Text(text = appBarTitle)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            // 네비게이션 구현
                            if (navController.currentBackStackEntry?.destination?.route != start) {
                                navController.popBackStack()
                            } else {
                                navigateToMyPage()
                            }
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back),
                                contentDescription = "뒤로가기 버튼"
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
//            if (navController.currentDestination?.route))
            NavHost(
                navController = navController,
                startDestination = start,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(start) {

                    MainEditInfoScreen(
                        viewModel = FakeMainEditInfoScreenViewModel(),
                        saveHandle = navController.currentBackStackEntry?.savedStateHandle!!,
                        action = listOf(
                            // 사진 0
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route)},
                            // 음성 1
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route)},
                            // 위치 2
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route)},
                            // 종교 3
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.ReligionEditScreen.route)},
                            // 흡연 4
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.SmokingEditScreen.route)},
                            // 음주 5
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.AlcoholEditScreen.route)},
                            // MBTI 6
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.MBTIEditScreen.route)},
                            // 관심사 7
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.InterestEditScreen.route)},
                            // 매칭 관계 8
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.MatchingEditScreen.route)}
                        )
                    )
                }

                // 내 정보 수정
                // 사진 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route) {
                    PotoEditInfoScreen(
                        viewModel = PotoEditInfoScreenViewModel()
                    ) {
                        navController.popBackStack()
                    }
                }
                //음성 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route) {

                }
                // 위치 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route) {
                LocationEditInfoScreen(
                    viewModel = LocationEditInfoScreenViewModel(
                        topLocationUseCase = topLocationUseCase,
                        bottomLocationUseCase = bottomLocationUseCase,
                        currentLocationSetUseCase = currentLocationSetUseCase
                    ),
                    popBackStack = {location->
                        navController
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("location", location)
                        navController.popBackStack()
                    }
                    )
                }
                // 종교 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.ReligionEditScreen.route) {
                    AdditionalInfoReligionScreen(viewModel = FakeAdditionalInfoReligionScreenViewModel(),
                        popBackStack = {pubList->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("religion", pubList)
                            navController.popBackStack()
                        }
                    )
                }
                // 흡연 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.SmokingEditScreen.route) {
                    AdditionalInfoSmokingScreen(viewModel = FakeAdditionalInfoSmokingScreenViewModel(),
                        popBackStack = {navController.popBackStack()}
                    )
                }
                // 음주 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.AlcoholEditScreen.route) {
                    AdditionalInfoAlcoholScreen(
                        viewModel = FakeAdditionalInfoAlcoholScreenViewModel(),
                        popBackStack = {navController.popBackStack()}
                    )
                }
                // MBTI 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MBTIEditScreen.route) {
                    MBTIEditInfoScreen(
                        viewModel = FakeMBTIEditInfoScreenViewModel(),
                        popBackStack = {navController.popBackStack()}
                    )
                }
                // 관심사 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.InterestEditScreen.route) {
                    InterestEditInfoScreen(
                        viewModel = FakeInterestEditInfoScreenViewModel(),
                        popBackStack = {navController.popBackStack()}
                    )
                }
                // 매칭 관계 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MatchingEditScreen.route) {
                    MatchingEditInfoScreen(
                        viewModel = FakeMatchingEditInfoScreenViewModel(),
                        popBackStack = {navController.popBackStack()}
                    )
                }
            }
        }
    }