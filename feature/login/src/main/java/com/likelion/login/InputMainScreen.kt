package com.likelion.login

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.likelion.login.agree_to_terms_screen.AgreeToTermsScreen
import com.likelion.login.agree_to_terms_screen.FakeAgreeToTermsScreenViewModel
import com.likelion.login.fifth_login_info_page.FakeFifthLoginInfoScreenViewModel
import com.likelion.login.fifth_login_info_page.FifthLoginInfoScreen
import com.likelion.login.fifth_login_info_page.FifthLoginInfoScreenViewModel
import com.likelion.login.fifth_login_info_page.FifthLoginInfoScreenViewModelType
import com.likelion.login.first_loginInfo_screen.FirstLoginInfoScreen
import com.likelion.login.first_login_info_screen.FakeFirstLoginInfoScreenViewModel
import com.likelion.login.forth_login_info_page.FourthLoginInfoScreen
import com.likelion.login.last_login_info_page.LastLoginInfoScreen
import com.likelion.login.login_start_screen.LoginStartScreen
import com.likelion.login.second_login_info_page.FakeSecondLoginInfoScreenViewModel
import com.likelion.login.second_login_info_page.SecondLoginInfoScreen
import com.likelion.login.third_login_info_page.FakeThirdLoginScreenViewModel
import com.likelion.login.third_login_info_page.ThirdLoginInfoScreen
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme

@Composable
fun InputRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    InputMainScreen(onNavigateUp = onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputMainScreen(
    onNavigateUp: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SisoColorTokens.White,
                    titleContentColor = SisoColorTokens.GrayScale90
                ),
                title = {
                    Text(text = "내 정보 입력")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // 네비게이션 구현
                        if (navController.currentBackStackEntry?.destination?.route != "main") {
                            navController.popBackStack()
                        } else {
                            onNavigateUp()
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "뒤로가기 버튼"
                        )
                    }
                },

                )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login1", // 약관동의
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login1") {
                AgreeToTermsScreen(
                    FakeAgreeToTermsScreenViewModel(),
                    onNavigateNext = {
                        navController.navigate("login2")
                    })
            }
            composable("login2") { // 가입화면
                LoginStartScreen(
                    onNavigateNext = {
                        navController.navigate("screen1")
                    }
                )
            }
            composable("screen1") {
                FirstLoginInfoScreen(
                    FakeFirstLoginInfoScreenViewModel(),
                    onNavigateNext = {
                        navController.navigate("screen2")
                    })
            }
            composable("screen2") {
                SecondLoginInfoScreen(
                    FakeSecondLoginInfoScreenViewModel(),
                    onNavigateNext = {
                        navController.navigate("screen3")
                    })
            }
            composable("screen3") {
                ThirdLoginInfoScreen(
                    FakeThirdLoginScreenViewModel(LocalContext.current),
                    onNavigateNext = {
                        navController.navigate("screen4")
                    })
            }
            composable("screen4") {
                FourthLoginInfoScreen(
                    onNavigateNext = {
                        navController.navigate("screen5")
                    })
            }
            composable("screen5") {
                FifthLoginInfoScreen(
                    viewModel = hiltViewModel<FifthLoginInfoScreenViewModel>(),
                    onNavigateNext = { navController.navigate("screen6") }
                )
            }
            composable("screen6") {
                LastLoginInfoScreen(
                    onNavigation = {
                        
                    }
                )

            }
        }
    }
}

@Composable
fun InputScreen1(
    onNavigateNext: () -> Unit
) {
    Column {
        Text("First")
        Button(onClick = onNavigateNext) {
            Text("go Second")
        }
    }
}


@Composable
@Preview
fun InputScreenPreview() {
    SisoTheme {
        InputMainScreen(onNavigateUp = {})
    }
}