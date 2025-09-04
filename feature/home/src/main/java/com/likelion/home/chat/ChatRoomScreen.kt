package com.likelion.home.chat

import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.home.chat.component.ChatBox
import com.likelion.home.chat.component.ChatTextField
import com.likelion.home.chat.component.MyChatBox
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ChatRoomRoute(
    chatRoomId: Long,
    nickname: String,
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    ChatRoomScreen(
        actionSnackbar = actionSnackbar,
        nickName = nickname,
        chatRoomId = chatRoomId,
        onNavigateUp = onNavigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    actionSnackbar: () -> Unit,
    nickName: String,
    chatRoomId: Long,
    onNavigateUp: () -> Unit
) {
    Timber.d("현재 상태의 방정보 nickname: $nickName, roomId: $chatRoomId")
    val viewModel = hiltViewModel<ChatViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    // BottomSheet 상태
    var showSheet by remember { mutableStateOf(false) }

    val bubbles = remember { mutableStateListOf<UiBubble>() }
    var partnerCount by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
// "최대 5개" 에러를 감지해 스낵바 표시
    LaunchedEffect(uiState.error) {
        if (uiState.error == "최대5") {
            snackbarHostState.showSnackbar(
                message = "메시지 제한을 모두 사용했어요. \n" +
                        "인연을 이어가면 제한이 해제됩니다.",
                //actionLabel = "확인",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            // 필요하면 에러를 소비하는 액션을 호출 (중복 노출 방지)
            // viewModel.clearError()
        }
    }
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

    if (showSheet) {
        ChatBottomSheet(
            actionSnackbar = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "신고가 접수되었습니다.",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onDismiss = { showSheet = false },
            viewModel = viewModel,
            chatRoomId = chatRoomId,
            onNavigateUp = onNavigateUp
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = nickName, style = SisoTypoTokens.Title2)
                },
                navigationIcon = {
                    val isChatRoomClick = remember { mutableStateOf(false) }
                    IconButton(onClick = {
                        if (!isChatRoomClick.value) {
                            isChatRoomClick.value = true
                            onNavigateUp()
                        }
                    }) {
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
                    IconButton(
                        onClick = {
                            showSheet = true
                        }
                    ) {
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
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 12.dp, vertical = 0.dp)
                    .offset(y = (-3).dp)
            ) {
                ChatTextField(
                    query = message,
                    onQueryUpdate = { message = it },
                    onEnter = {
                        if (chatRoomId.toInt() == 1) {
                            viewModel.handleEvent(ChatEvent.SendChat(message))
                        } else {
                            viewModel.handleEvent(
                                ChatEvent.SendChatLimit(
                                    chatRoomId = chatRoomId,
                                    chat = message
                                )
                            )
                        }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomSheet(
    actionSnackbar: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: ChatViewModel,
    chatRoomId: Long,
    onNavigateUp: () -> Unit,

    ) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .wrapContentHeight(),
        containerColor = SisoColorTokens.White,
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "채팅방 나가기", color = SisoColorTokens.Red60, style = SisoTypoTokens.Body2,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        viewModel.removeChatHistory(chatRoomId)
                        onDismiss()
                        onNavigateUp()
                    }
            )
            Text(
                text = "신고하기", color = SisoColorTokens.Gray90, style = SisoTypoTokens.Body2,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        viewModel.removeChatHistory(chatRoomId)
                        onDismiss()
                        actionSnackbar()
                        onNavigateUp()
                    },
            )
            Text(
                text = "취소", color = SisoColorTokens.Gray90, style = SisoTypoTokens.Body2,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onDismiss()
                    },
            )
        }
    }
}

@Composable
@Preview
fun ChatRoomScreenPreview() {
    SisoTheme {
        ChatRoomScreen(
            nickName = "", chatRoomId = 0, onNavigateUp = {},
            actionSnackbar = {}
        )
    }
}

@Composable
@Preview
private fun BottomSheetPreview() {
    SisoTheme {
        ChatBottomSheet(
            onDismiss = {},
            viewModel = hiltViewModel(),
            chatRoomId = 0,
            onNavigateUp = {},
            actionSnackbar = {}
        )
    }
}