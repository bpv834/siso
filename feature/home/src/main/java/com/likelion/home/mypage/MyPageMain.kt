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
                    titleContentColor = SisoColorTokens.Gray90
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