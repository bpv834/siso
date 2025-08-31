package com.likelion.home.navigation.edit_Main

import android.annotation.SuppressLint
import android.util.Log.d
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.AdditionalInfoAlcoholScreen
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.AdditionalInfoAlcoholScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.AdditionalInfoReligionScreen
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.AdditionalInfoReligionScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.AdditionalInfoSmokingScreen
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.AdditionalInfoSmokingScreenViewModel
import com.likelion.home.mypage.interest_edit_info_screen.InterestEditInfoScreen
import com.likelion.home.mypage.interest_edit_info_screen.InterestEditInfoScreenViewModel
import com.likelion.home.mypage.location_edit_info_screen.LocationEditInfoScreen
import com.likelion.home.mypage.location_edit_info_screen.LocationEditInfoScreenViewModel
import com.likelion.home.mypage.main_edit_info_screen.MainEditInfoScreen
import com.likelion.home.mypage.main_edit_info_screen.MainEditInfoScreenViewModel
import com.likelion.home.mypage.matching_edit_info_screen.MatchingEditInfoScreen
import com.likelion.home.mypage.matching_edit_info_screen.MatchingEditInfoScreenViewModel
import com.likelion.home.mypage.mbti_edit_info_screen.MBTIEditInfoScreen
import com.likelion.home.mypage.mbti_edit_info_screen.MBTIEditInfoScreenViewModel
import com.likelion.home.mypage.poto_edit_info_screen.PotoEditInfoScreen
import com.likelion.home.mypage.poto_edit_info_screen.PotoEditInfoScreenViewModel
import com.likelion.home.navigation.getString
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import kotlinx.coroutines.flow.first

@Composable
fun EditMainRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {}
) {
    SisoTheme {
        EditMain(
            navigateToMyPage = actionSnackbar
        )
    }

}

@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition", "TimberArgCount")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMain (
    navigateToMyPage : () -> Unit = {},
) {
    val title = stringResource(com.likelion.home.R.string.main_edit)
    val navController = rememberNavController()
    var appBarTitle by remember { mutableStateOf(title) }
    val start = NavigationRoute.MyPageScreen.MainEditScreen.route
    val preSavedStateHandle = navController.previousBackStackEntry?.savedStateHandle

    val mainEditInfoScreenViewModel = hiltViewModel<MainEditInfoScreenViewModel>()
    mainEditInfoScreenViewModel.fetchUsers(13L,13L) // 유저 정보를 가져오는 곳
    val photoEditInfoScreenViewModel = hiltViewModel<PotoEditInfoScreenViewModel>()
    val additionalInfoSmokingScreenViewModel = hiltViewModel<AdditionalInfoSmokingScreenViewModel>()
    val additionalInfoAlcoholScreenViewModel = hiltViewModel<AdditionalInfoAlcoholScreenViewModel>()
    val mbtiEditInfoScreenViewModel = hiltViewModel<MBTIEditInfoScreenViewModel>()
    val religionEditInfoScreenViewModel = hiltViewModel<AdditionalInfoReligionScreenViewModel>()
    val matchingEditInfoScreenViewModel = hiltViewModel<MatchingEditInfoScreenViewModel>()
    val interestEditInfoScreenViewModel = hiltViewModel<InterestEditInfoScreenViewModel>()

    // 종교 화면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.getString("religion") != null){
        religionEditInfoScreenViewModel.fetch(preSavedStateHandle.getString("religion")!!)
    }

    // 흡연 화면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.getString("smoking") != null){
        additionalInfoSmokingScreenViewModel.fetch(preSavedStateHandle.getString("smoking")!!)
    }

    // 음주 화면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.getString("alcohol") != null){
        additionalInfoAlcoholScreenViewModel.fetch(preSavedStateHandle.getString("alcohol")!!)
    }

    // MBTI 화면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.getString("mbti") != null){
        mbtiEditInfoScreenViewModel.setReceiver(preSavedStateHandle.getString("mbti")!!)
    }

    // 관심사 화면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.get<List<String>>("interest") != null){
        interestEditInfoScreenViewModel.fetch(preSavedStateHandle.get<List<String>>("interest")!!)
    }

    // 매칭 인연 선택 하면에 해당 값 삽입
    if(preSavedStateHandle != null && preSavedStateHandle.get<List<String>>("matching") != null){
        matchingEditInfoScreenViewModel.fetch(preSavedStateHandle.get<List<String>>("matching")!!)
    }

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
                        viewModel = mainEditInfoScreenViewModel,
                        saveHandle = navController.currentBackStackEntry?.savedStateHandle!!,
                        naviToMyPage = {navigateToMyPage()},
                        action = listOf(
                            // 사진 0
                            {
                                // 포토에 list 값을 보냄
                                photoEditInfoScreenViewModel.fetch(mainEditInfoScreenViewModel.userImages)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route)
                            },
                            // 음성 1
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route)},
                            // 위치 2
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route)},
                            // 종교 3
                            { navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.ReligionEditScreen.route) },
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

                val user = mainEditInfoScreenViewModel.uiState.value.receiverUsersModel
                composable(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route) {
                    PotoEditInfoScreen(
                        viewModel = photoEditInfoScreenViewModel
                    ) {
                        val editAllNotNull = !photoEditInfoScreenViewModel.capturedImages.value.all { it.edited == null }
                        // 완료 될 경우
                        if (editAllNotNull) {
                            // 비어있지 않은 경우에만 main의 프로필과 사진을 바꿔준다
                            d("userImages","${photoEditInfoScreenViewModel.capturedImages.value}")
                            mainEditInfoScreenViewModel.userImages(photoEditInfoScreenViewModel.capturedImages.value)
                            navController.popBackStack()
                        }
                    }
                }
                //음성 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route) {

                }
                // 위치 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route) {
                LocationEditInfoScreen(
                    viewModel = hiltViewModel<LocationEditInfoScreenViewModel>(),
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
                    AdditionalInfoReligionScreen(
                        viewModel = religionEditInfoScreenViewModel,
                        popBackStack = {religion->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("religion", religion)
                            navController.popBackStack()
                        }
                    )
                }
                // 흡연 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.SmokingEditScreen.route) {
                    AdditionalInfoSmokingScreen(viewModel = additionalInfoSmokingScreenViewModel,
                        popBackStack = {smoking->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("smoking", smoking)
                            navController.popBackStack()
                        }
                    )
                }
                // 음주 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.AlcoholEditScreen.route) {
                    AdditionalInfoAlcoholScreen(
                        viewModel = additionalInfoAlcoholScreenViewModel,
                        popBackStack = {
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("alcohol", it)
                            navController.popBackStack()
                        }
                    )
                }
                // MBTI 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MBTIEditScreen.route) {
                    MBTIEditInfoScreen(
                        viewModel = mbtiEditInfoScreenViewModel,
                        popBackStack = {mbti->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("mbti", mbti)
                            navController.popBackStack()
                        }
                    )
                }
                // 관심사 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.InterestEditScreen.route) {
                    InterestEditInfoScreen(
                        viewModel = interestEditInfoScreenViewModel,
                        popBackStack = {interest->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("interest", interest)
                            navController.popBackStack()
                        }
                    )
                }
                // 매칭 관계 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MatchingEditScreen.route) {
                    MatchingEditInfoScreen(
                        viewModel = matchingEditInfoScreenViewModel,
                        popBackStack = {
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("matching", it)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }