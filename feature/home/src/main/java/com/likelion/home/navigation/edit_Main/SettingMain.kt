package com.likelion.home.navigation.edit_Main

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
import com.likelion.home.mypage.setting_screen.SettingScreen
import com.likelion.home.mypage.setting_screen.call_edit_screen.SettingCallEditScreen
import com.likelion.navigation.NavigationRoute
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens

@Composable
fun SettingMainRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {}
) {
    SettingMain(navigateToMyPage = actionSnackbar)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingMain (
    navigateToMyPage : () -> Unit = {},
){
    val title = stringResource(com.likelion.home.R.string.setting)
    val navController = rememberNavController()
    var appBarTitle by remember { mutableStateOf(title) }
    val start = NavigationRoute.MyPageScreen.SettingScreen.route
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
        NavHost(
            navController = navController,
            startDestination = start,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(start) {

                SettingScreen(
                    action = listOf(
                        // 계정 0
                        {  },
                        // 알림 1
                        { navController.navigate(NavigationRoute.MyPageScreen.SettingScreen.CallEditScreen.route) },
                        // 결제 내역 조회 2
                        { },
                        // 개인정보 처리방침 3
                        { },
                        // 법적고지 4
                        { },
                        // 로그아웃 5
                        { },
                        // 회원탈퇴 6
                        { },
                    )
                )
            }

            // 설정 메뉴
            composable(NavigationRoute.MyPageScreen.SettingScreen.CallEditScreen.route) {
                SettingCallEditScreen()
            }
        }
    }
}