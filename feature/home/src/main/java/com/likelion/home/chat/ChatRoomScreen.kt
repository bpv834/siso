package com.likelion.home.chat


import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.domain.chat.model.MyChat
import com.likelion.home.chat.component.ChatBox
import com.likelion.home.chat.component.ChatTextField
import com.likelion.home.chat.component.MyChatBox
import com.likelion.ui.R
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
    val viewModel = hiltViewModel<ChatViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val bubbles = remember { mutableStateListOf<UiBubble>() }
    var partnerCount by remember { mutableStateOf(0) }

    LaunchedEffect(uiState.myChat) {
        uiState.myChat?.let { bubbles += UiBubble.Mine(it) }
    }
    LaunchedEffect(uiState.partnerChatList.size) {
        val incoming = uiState.partnerChatList
        if (incoming.size > partnerCount) {
            incoming.drop(partnerCount).forEach { bubbles += UiBubble.Partner(it) }
            partnerCount = incoming.size
        }
    }
    LaunchedEffect(bubbles.size) {
        if (bubbles.isNotEmpty()) {
            listState.animateScrollToItem(bubbles.lastIndex)
        }
    }




    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = nickName, style = SisoTypoTokens.Title2)
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
            var message by remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .navigationBarsPadding()      // avoid 3-button/gesture nav bar when IME is hidden
                    .imePadding()                 // lift above keyboard when IME is shown
                    .padding(horizontal = 12.dp, vertical = 0.dp)
                    .offset(y = (-3).dp)
            ) {
                ChatTextField(
                    query = message,
                    onQueryUpdate = { message = it },
                    onEnter = {
                        viewModel.handleEvent(ChatEvent.SendChat(message))
                        message = ""
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(state = listState) {
                itemsIndexed(
                    items = bubbles,
                    key = { index, item ->
                        when (item) {
                            is UiBubble.Mine -> "mine-" + index
                            is UiBubble.Partner -> "partner-" + index
                        }
                    }
                ) { index, bubble ->
                    val next = bubbles.getOrNull(index + 1)
                    val currentTime = when (bubble) {
                        is UiBubble.Mine -> bubble.data.time
                        is UiBubble.Partner -> bubble.data.partnerTime
                    }.trim().take(5)
                    val nextTime = when (next) {
                        null -> null
                        is UiBubble.Mine -> next.data.time.trim().take(5)
                        is UiBubble.Partner -> next.data.partnerTime.trim().take(5)
                    }
                    val showTime = nextTime == null || nextTime != currentTime

                    when (bubble) {
                        is UiBubble.Mine -> MyChatBox(bubble.data.copy(showTime = showTime))
                        is UiBubble.Partner -> ChatBox(bubble.data.copy(showTime = showTime))
                    }
                }
            }
        }
    }
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
        val chat = MyChat(
            msg = "ㅁㄴㅇㄹ먼ㅇ;ㅣ림ㄴ;아러ㅣㅏㅁㅇㄴ",
            time = "12:25",
            showTime = true
        )
        MyChatBox(chat)
    }
}