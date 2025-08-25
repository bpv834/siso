package com.likelion.home.mypage

import android.view.View
import androidx.compose.foundation.layout.Box
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
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.AdditionalInfoAlcoholScreen
import com.likelion.home.mypage.additional_info.additional_info_alcohol_screen.FakeAdditionalInfoAlcoholScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.AdditionalInfoReligionScreen
import com.likelion.home.mypage.additional_info.additional_info_religion_screen.FakeAdditionalInfoReligionScreenViewModel
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.AdditionalInfoSmokingScreen
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.FakeAdditionalInfoSmokingScreenViewModel
import com.likelion.home.mypage.interest_edit_info_screen.FakeInterestEditInfoScreenViewModel
import com.likelion.home.mypage.interest_edit_info_screen.InterestEditInfoScreen
import com.likelion.home.mypage.main_edit_info_screen.FakeMainEditInfoScreenViewModel
import com.likelion.home.mypage.main_edit_info_screen.MainEditInfoScreen
import com.likelion.home.mypage.matching_edit_info_screen.FakeMatchingEditInfoScreenViewModel
import com.likelion.home.mypage.matching_edit_info_screen.MatchingEditInfoScreen
import com.likelion.home.mypage.mbti_edit_info_screen.FakeMBTIEditInfoScreenViewModel
import com.likelion.home.mypage.mbti_edit_info_screen.MBTIEditInfoScreen
import com.likelion.home.mypage.setting_screen.SettingScreen
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    navigateToHome: () -> Unit = {},
    mainEdit: () -> Unit = {},
    setting: () -> Unit = {},
    actionSnackbar: () -> Unit = {}
) {
    MyPageMainScreen(navigateToHome, mainEdit, setting)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageMainScreen(
    navigateToHome : () -> Unit = {},
    mainEdit: () -> Unit = {},
    setting: () -> Unit = {},
) {
    var appBarTitle by remember { mutableStateOf("내 정보") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SisoColorTokens.White,
                    titleContentColor = SisoColorTokens.GrayScale90
                ),
                title = {
                    Text(text = appBarTitle)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // 네비게이션 구현
                        navigateToHome()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "뒤로가기 버튼"
                        )
                    }
                },
                actions = {

                        IconButton(onClick = {
                            setting()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_setting),
                                contentDescription = "설정 버튼"
                            )
                        }

                }

            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MyPageScreen(mainEdit = {
                mainEdit()
            })
        }
    }
}