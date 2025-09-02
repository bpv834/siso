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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.likelion.home.mypage.record_edit_info_screen.RecordEditInfoScreen
import com.likelion.home.mypage.record_edit_info_screen.RecordEditInfoScreenViewModel
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

    val mainViewModel = hiltViewModel<MainEditInfoScreenViewModel>()
    val tempAccess = "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImtnODQ4MEBnbWFpbC5jb20iLCJpYXQiOjE3NTY3ODAzNjEsImV4cCI6MTc1Nzk4OTk2MX0.7JD1bTDaEzO9bcrKJcit44JaWSg0HVcDeU76tfPZyqM"
    mainViewModel.fetchUsers(tempAccess,13L) // 유저 정보를 가져오는 곳
    val photoEditInfoScreenViewModel = hiltViewModel<PotoEditInfoScreenViewModel>()
    val recordEditInfoScreenViewModel = hiltViewModel<RecordEditInfoScreenViewModel>()
    val additionalInfoSmokingScreenViewModel = hiltViewModel<AdditionalInfoSmokingScreenViewModel>()
    val additionalInfoAlcoholScreenViewModel = hiltViewModel<AdditionalInfoAlcoholScreenViewModel>()
    val mbtiEditInfoScreenViewModel = hiltViewModel<MBTIEditInfoScreenViewModel>()
    val religionEditInfoScreenViewModel = hiltViewModel<AdditionalInfoReligionScreenViewModel>()
    val matchingEditInfoScreenViewModel = hiltViewModel<MatchingEditInfoScreenViewModel>()
    val interestEditInfoScreenViewModel = hiltViewModel<InterestEditInfoScreenViewModel>()

    recordEditInfoScreenViewModel.fetchAudioBytes(mainViewModel.uiState.value.editUsersModel.voiceUrl)

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
                        viewModel = mainViewModel,
                        saveHandle = navController.currentBackStackEntry?.savedStateHandle!!,
                        naviToMyPage = {navigateToMyPage()},
                        action = listOf(
                            // 사진 0
                            {
                                // 포토에 list 값을 보냄
                                photoEditInfoScreenViewModel.fetch(mainViewModel.userImages)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route)
                            },
                            // 음성 1
                            {
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route)
                            },
                            // 위치 2
                            {navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route)},
                            // 종교 3
                            {
                                religionEditInfoScreenViewModel.fetch(mainViewModel.uiState.value.editUsersModel.religion)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.ReligionEditScreen.route)
                            },
                            // 흡연 4
                            {
                                additionalInfoSmokingScreenViewModel.fetch(mainViewModel.uiState.value.editUsersModel.isSmoke)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.SmokingEditScreen.route)
                            },
                            // 음주 5
                            {
                                additionalInfoAlcoholScreenViewModel.fetch(mainViewModel.uiState.value.editUsersModel.drinkingCapacity)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.AlcoholEditScreen.route)
                            },
                            // MBTI 6
                            {
                                mbtiEditInfoScreenViewModel.setReceiver(mainViewModel.uiState.value.editUsersModel.mbti)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.MBTIEditScreen.route)
                            },
                            // 관심사 7
                            {
                                interestEditInfoScreenViewModel.fetch(mainViewModel.uiState.value.editUsersModel.interests)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.InterestEditScreen.route)
                            },
                            // 매칭 관계 8
                            {
                                matchingEditInfoScreenViewModel.fetch(mainViewModel.uiState.value.editUsersModel.meeting)
                                navController.navigate(NavigationRoute.MyPageScreen.MainEditScreen.MatchingEditScreen.route)
                            }
                        )
                    )
                }

                // 내 정보 수정
                // 사진 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.PotoEditScreen.route) {
                    PotoEditInfoScreen(
                        viewModel = photoEditInfoScreenViewModel
                    ) {
                        val editAllNotNull = !photoEditInfoScreenViewModel.capturedImages.value.all { it.edited == null }
                        // 완료 될 경우
                        if (editAllNotNull) {
                            // 비어있지 않은 경우에만 main의 프로필과 사진을 바꿔준다
                            d("userImages","${photoEditInfoScreenViewModel.capturedImages.value}")
                            mainViewModel.userImages(photoEditInfoScreenViewModel.capturedImages.value)
                            navController.popBackStack()
                        }
                    }
                }
                //음성 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.VoiceEditScreen.route) {
                    RecordEditInfoScreen(
                        recordEditInfoScreenViewModel,
                    ){ voice ->
                        recordEditInfoScreenViewModel.fetchAudioBytes(voice)
                        mainViewModel.setVoiceUrl(voiceUrl = voice)
                        navController.popBackStack()
                    }
                }
                // 위치 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.LocationEditScreen.route) {
                LocationEditInfoScreen(
                    viewModel = hiltViewModel<LocationEditInfoScreenViewModel>(),
                    popBackStack = {location->
                        mainViewModel.setLocation(location)
                        navController.popBackStack()
                    }
                    )
                }
                // 종교 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.ReligionEditScreen.route) {
                    AdditionalInfoReligionScreen(
                        viewModel = religionEditInfoScreenViewModel,
                        popBackStack = {religion->
                            mainViewModel.setReligion(religion)
                            navController.popBackStack()
                        }
                    )
                }
                // 흡연 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.SmokingEditScreen.route) {
                    AdditionalInfoSmokingScreen(viewModel = additionalInfoSmokingScreenViewModel,
                        popBackStack = {smoking->
                            mainViewModel.setSmoking(smoking)
                            navController.popBackStack()
                        }
                    )
                }
                // 음주 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.AlcoholEditScreen.route) {
                    AdditionalInfoAlcoholScreen(
                        viewModel = additionalInfoAlcoholScreenViewModel,
                        popBackStack = {
                            mainViewModel.setAlcohol(it)
                            navController.popBackStack()
                        }
                    )
                }
                // MBTI 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MBTIEditScreen.route) {
                    MBTIEditInfoScreen(
                        viewModel = mbtiEditInfoScreenViewModel,
                        popBackStack = {mbti->
                            mainViewModel.setMbti(mbti)
                            navController.popBackStack()
                        }
                    )
                }
                // 관심사 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.InterestEditScreen.route) {
                    InterestEditInfoScreen(
                        viewModel = interestEditInfoScreenViewModel,
                        popBackStack = {interest->
                            mainViewModel.setInterest(interest)
                            navController.popBackStack()
                        }
                    )
                }
                // 매칭 관계 수정
                composable(NavigationRoute.MyPageScreen.MainEditScreen.MatchingEditScreen.route) {
                    MatchingEditInfoScreen(
                        viewModel = matchingEditInfoScreenViewModel,
                        popBackStack = { matching->
                            mainViewModel.setMatching(matching)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }