package com.likelion.home.chat


import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun ChatRoomRoute(
    nickname: String,
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    ChatRoomScreen(nickname, onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    nickName: String,
    onNavigateUp: () -> Unit

) {
    var message by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = nickName)
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_call),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_vertical_dot),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp),
                    placeholder = { Text("메시지 입력…") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            // TODO: send message
                            message = ""
                        }
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    // TODO: send message
                    message = ""
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_send), // TODO: replace with ic_send
                        contentDescription = "보내기"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            val text = "이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요"
            val text2= "이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요 이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요 이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요"
            MyChatBox(text)
            MyChatBox(text2)
        }
    }
}

@Composable
fun MyChatBox(text: String) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            text = "18:25", style = SisoTypoTokens.Caption1, color = SisoColorTokens.Gray50, modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 6.dp, end = 8.dp)
        )
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = SisoColorTokens.Gold30,
                    shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp, topEnd = 14.dp)
                )
        ) {
            Text(
                text = text,
                style = SisoTypoTokens.Body3,
                color = SisoColorTokens.Gray90,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }

}

@Composable
@Preview
fun ChatTextField() {

}

@Composable
@Preview
fun ChatRoomScreenPreview() {
    SisoTheme {
        ChatRoomScreen(nickName = "", {})
    }
}

@Composable
@Preview
fun MyChatBoxPreview() {
    SisoTheme {
        MyChatBox("이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요")
    }
}