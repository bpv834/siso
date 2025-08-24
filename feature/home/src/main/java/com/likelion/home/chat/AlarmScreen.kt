package com.likelion.home.chat

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun AlarmRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    AlarmScreen(onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    onNavigateChat: () -> Unit
) {
    val isAlarmClick = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "알림", style = SisoTypoTokens.Title3) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!isAlarmClick.value) {
                            isAlarmClick.value = true
                            onNavigateChat()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "뒤로가기"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.size(24.dp))
            val title = "새로운 인연이 부재중을 남겼어요"
            val content = "알림내용입니다. 해당알림을 터치하면 채팅창으로 이동하여 새로운 인연이 남긴 부재중을 확인할 수 있어요"
            AlarmMsgBox(title, content)
        }
    }
}

@Composable
fun AlarmMsgBox(
    title: String,
    content: String,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title, style = SisoTypoTokens.SubTitle1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        Text(
            text = content,
            style = SisoTypoTokens.Body4,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 16.dp)
        )
        HorizontalDivider(color = SisoColorTokens.Gray30)

    }
}

@Composable
@Preview
fun AlarmPreviewScreen() {
    SisoTheme {
        AlarmScreen({})
    }
}