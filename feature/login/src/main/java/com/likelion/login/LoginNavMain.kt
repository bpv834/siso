package com.likelion.login

import android.util.Log
import android.view.View
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.likelion.login.first_loginInfo_screen.FirstLoginInfoScreen
import com.likelion.login.login_agree.AgreeToTermsScreen
import com.likelion.login.login_agree.FakeAgreeToTermsScreenViewModel
import com.likelion.login.login_agree2.LoginStartScreen
import com.likelion.login.login_end.LastLoginInfoScreen
import com.likelion.login.login_input_hobby.SecondLoginInfoScreen
import com.likelion.login.login_input_hobby.SecondLoginInfoScreenViewModel
import com.likelion.login.login_input_info.FirstLoginInfoScreenViewModel
import com.likelion.login.login_input_introduce.FourthLoginInfoScreen
import com.likelion.login.login_input_introduce.FourthLoginInfoScreenViewModel
import com.likelion.login.login_input_photo.ThirdLoginInfoScreen
import com.likelion.login.login_input_photo.ThirdLoginInfoScreenViewModel
import com.likelion.login.login_input_record.FifthLoginInfoScreen
import com.likelion.login.login_input_record.FifthLoginInfoScreenViewModel
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme

@Composable
fun InputRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateInit: () -> Unit = {},
    onExitRegister: () -> Unit = {}
) {
    LoginMainScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToHome = onNavigateToHome,
        onNavigateInit = onNavigateInit,
        onExitRegister = onExitRegister,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginMainScreen(
    onNavigateUp: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateInit: () -> Unit,
    onExitRegister: () -> Unit
) {
    val navController = rememberNavController()
    BackHandler {
        when (navController.currentBackStackEntry?.destination?.route) {
            "login1" -> {
                // Exit register flow: reset state then go to login root
                onExitRegister()
                onNavigateInit()
            }
            "main" -> {
                onNavigateUp()
            }
            else -> {
                navController.popBackStack()
            }
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SisoColorTokens.White,
                    titleContentColor = SisoColorTokens.Gray90
                ),
                title = {
                    Text(text = "내 정보 입력")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // 네비게이션 구현
                        when (navController.currentBackStackEntry?.destination?.route) {
                            "login1" ->{
                                onExitRegister()
                                onNavigateInit()
                                Log.d("Nav","초기화")
                            }
                            "main" ->{
                                onNavigateUp()
                            }
                            else ->{
                                navController.popBackStack()
                            }
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
                    hiltViewModel<FirstLoginInfoScreenViewModel>(),
                    onNavigateNext = {
                        navController.navigate("screen2")
                    })
            }
            composable("screen2") {
                SecondLoginInfoScreen(
                    hiltViewModel<SecondLoginInfoScreenViewModel>(),
                    onNavigateNext = {
                        navController.navigate("screen3")
                    })
            }
            composable("screen3") {
                ThirdLoginInfoScreen(
                    hiltViewModel<ThirdLoginInfoScreenViewModel>(),
                    onNavigateNext = {
                        navController.navigate("screen4")
                    })
            }
            composable("screen4") {
                FourthLoginInfoScreen(
                    hiltViewModel<FourthLoginInfoScreenViewModel>(),
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
                        onNavigateToHome()
                    }
                )
            }
            composable("mainScreen") {

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
        LoginMainScreen(onNavigateUp = {}, {},{}, {})
    }
}