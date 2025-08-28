package com.likelion.home.mypage

import android.view.View
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.dp
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    mainEdit: () -> Unit = {},
    setting: () -> Unit = {},
    actionSnackbar: () -> Unit = {}
) {
    MyPageMainScreen( mainEdit = mainEdit, setting = setting)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageMainScreen(
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
        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            MyPageScreen(mainEdit = {
                mainEdit()
            })
        }
    }
}