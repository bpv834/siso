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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                Column {
                    Text("Main")
                    Button(onClick = {
                        navController.navigate("screen1")
                    }) {
                        Text("go First")
                    }
                }
            }
            composable("screen1") {
                InputScreen1(
                    onNavigateNext = {
                        navController.navigate("screen2")
                    }
                )
            }
            composable("screen2") {
                InputScreen2()
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
fun InputScreen2() {
    Column {
        Text("second")
        Button(onClick = {}) {
            Text("go third")
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