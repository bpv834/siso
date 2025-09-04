package com.likelion.home.chat

import android.R.attr.onClick
import android.view.View
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.domain.chat.model.CallHistory
import com.likelion.domain.chat.model.ChatHistory
import com.likelion.ui.R
import com.likelion.ui.component.shimmer.shimmerEffect
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ChatRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateAlarm: () -> Unit = {},
    onNavigateChatRoom: (String, Long) -> Unit = { _, _ -> },
    onNavigatePartner: () -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        viewModel = viewModel,
        onNavigateAlarm = { onNavigateAlarm() },
        onNavigateChatRoom = { nickname, chatRoomId ->
            onNavigateChatRoom(nickname, chatRoomId)
        },
        onNavigatePartner = { onNavigatePartner() }
    )
}

@Composable
fun ChatScreen(
    onNavigateAlarm: () -> Unit,
    onNavigateChatRoom: (String, Long) -> Unit,
    onNavigatePartner: () -> Unit,
    viewModel: ChatViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    var chatLoaded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(SisoColorTokens.Gray5)
    ) {
        LaunchedEffect(pagerState.currentPage) {
            if (pagerState.currentPage == 1 && !chatLoaded) {
                chatLoaded = true
                viewModel.handleEvent(ChatEvent.LoadChatHistory)
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 52.dp, bottom = 22.dp)
        ) {
            Row {
                Text(
                    text = "전화내역", style = SisoTypoTokens.Title2,
                    color = if (pagerState.currentPage == 0) SisoColorTokens.Gray90 else SisoColorTokens.Gray50,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = 0,
                                    animationSpec = tween(durationMillis = 10)
                                )
                            }
                        }
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "대화내역",
                    style = SisoTypoTokens.Title2,
                    color = if (pagerState.currentPage == 1) SisoColorTokens.Gray90 else SisoColorTokens.Gray50,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = 1,
                                    animationSpec = tween(durationMillis = 10)
                                )
                            }
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    onNavigateAlarm()

                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_alarm),
                        contentDescription = ""
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    CallHistoryPage(
                        items = uiState.callHistory,
                        isLoading = uiState.isCallHistoryLoading,
                        onDelete = { id -> viewModel.handleEvent(ChatEvent.RemoveCallHistory(id)) },
                        onNavigatePartner = onNavigatePartner,
                    )
                    Timber.d("${uiState.callHistory}")
                }

                1 -> {
                    ChatHistoryPage(
                        items = uiState.chatHistory,
                        isLoading = uiState.isChatHistoryLoading,
                        onDelete = { id -> viewModel.handleEvent(ChatEvent.RemoveChatHistory(id)) },
                        onNavigateChatRoom = onNavigateChatRoom
                    )
                    Timber.d("${uiState.chatHistory}")
                }
            }
        }
    }
}


@Composable
fun ChatHistoryPage(
    items: List<ChatHistory>,
    isLoading: Boolean,
    onNavigateChatRoom: (String, Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    when {
        isLoading -> {
            LazyColumn(Modifier.fillMaxSize()) {
                items(8) {
                    SkeletonChatRow()
                }
            }
        }

        items.isEmpty() -> {
            EmptyChatHistory()
        }

        else -> {
            ChatHistoryList(
                items = items,
                onDelete = onDelete,
                onNavigateChatRoom = onNavigateChatRoom
            )
        }
    }
}


@Composable
fun CallHistoryPage(
    items: List<CallHistory>,
    isLoading: Boolean,
    onDelete: (Long) -> Unit,
    onNavigatePartner: () -> Unit
) {
    when {
        isLoading -> {
            LazyColumn(Modifier.fillMaxSize()) {
                items(8) { SkeletonCallRow() }
            }
        }

        items.isEmpty() -> {
            EmptyCallHistory()
        }

        else -> {
            CallHistoryList(
                items = items,
                onDelete = onDelete,
                onNavigatePartner = onNavigatePartner
            )
        }
    }
}

@Composable
fun ChatHistoryList(
    items: List<ChatHistory>,
    onDelete: (Long) -> Unit,
    onNavigateChatRoom: (String, Long) -> Unit,
) {
    var expandedId by rememberSaveable { mutableStateOf<Long?>(null) }

    // 리스트가 바뀌면(삭제 등) 자동으로 닫기
    LaunchedEffect(items.size) { expandedId = null }

    if (items.isEmpty()) {
        EmptyChatHistory()
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(
                items,
                key = { _, contact -> contact.chatRoomId }   // ✅ 안정 key 필수
            ) { _, contact ->

                val isRevealed = expandedId == contact.chatRoomId

                SwipeableItemWithActions(
                    isRevealed = isRevealed,
                    onExpanded = { expandedId = contact.chatRoomId },              // ✅ '=' (대입)
                    onCollapsed = { if (expandedId == contact.chatRoomId) expandedId = null },
                    actions = {
                        ActionIcon(
                            text = "나가기",
                            onClick = {
                                expandedId = null          // ✅ 삭제 전에 닫기
                                onDelete(contact.chatRoomId)
                                Timber.d("ChatRoom: ${contact.chatRoomId}, ${contact.nickName}")
                            },
                            backgroundColor = SisoColorTokens.Red60,
                            modifier = Modifier.fillMaxHeight()
                        )
                    },
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                expandedId = null         // (선택) 탭 시 닫기
                                onNavigateChatRoom(contact.nickName, contact.chatRoomId)
                            }
                    ) {
                        AsyncImage(
                            model = contact.profileImage,
                            contentDescription = "",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically),
                        )
                        Column(
                            modifier = Modifier
                                .padding(vertical = 13.dp)
                                .padding(start = 8.dp)
                        ) {
                            Text(text = contact.nickName, style = SisoTypoTokens.SubTitle1)
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = contact.currentMsg,
                                style = SisoTypoTokens.Body4,
                                maxLines = 1,
                                color = SisoColorTokens.Gray50,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Column(
                            modifier = Modifier
                                .padding(vertical = 13.dp)
                                .padding(start = 8.dp)
                        ) {
                            Text(text = contact.callTime, style = SisoTypoTokens.SubTitle1)
                            Spacer(modifier = Modifier.size(10.dp))
                            if (contact.isNew) {
                                AsyncImage(
                                    model = R.drawable.ic_new_msg,
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .align(Alignment.End)
                                        .size(24.dp),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CallHistoryList(
    items: List<CallHistory>,
    onDelete: (Long) -> Unit,
    onNavigatePartner: () -> Unit
) {
    var expandedId by rememberSaveable { mutableStateOf<Long?>(null) }

    // 리스트가 바뀌면(삭제 등) 자동으로 닫기
    LaunchedEffect(items.size) { expandedId = null }

    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(
            items,
            key = { _, contact -> contact.callId }
        ) { _, contact ->

            val isRevealed = expandedId == contact.callId

            SwipeableItemWithActions(
                isRevealed = isRevealed,
                onExpanded = { expandedId = contact.callId },
                onCollapsed = { if (expandedId == contact.callId) expandedId = null },
                actions = {
                    ActionIcon(
                        text = "인연끊기",
                        onClick = {
                            expandedId = null
                            onDelete(contact.callId)
                        },
                        backgroundColor = SisoColorTokens.Red60,
                        modifier = Modifier.fillMaxHeight()
                    )
                },
            ) {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(
                            onClick = onNavigatePartner

                        )
                ) {
                    AsyncImage(
                        model = contact.profileImage,
                        contentDescription = "",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = contact.nickName,
                        style = SisoTypoTokens.SubTitle1,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = contact.callTime,
                        style = SisoTypoTokens.Body4,
                        color = SisoColorTokens.Gray50,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
private fun SkeletonChatRow() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight()
    ) {
        // 프로필 원형 자리
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            // 이름 바
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.35f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.size(4.dp))
            // 이름 바
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.35f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(14.dp)
                .fillMaxWidth(0.15f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
    }
}

@Composable
private fun SkeletonCallRow() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight()
    ) {
        // 프로필 원형 자리
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            // 이름 바
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.35f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(14.dp)
                .fillMaxWidth(0.15f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionIcon(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(width = 120.dp, height = 80.dp)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = SisoTypoTokens.Body2,
            color = SisoColorTokens.White,
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 26.dp)
        )
    }
}

@Preview
@Composable
fun CallHistoryPreview() {
    val previewList = listOf(
        CallHistory(
            callId = 0,
            profileImage = "https://picsum.photos/200/200",
            nickName = "코틀린",
            callTime = "12:10"
        ),
        CallHistory(
            callId = 1,
            profileImage = "https://picsum.photos/200/201",
            nickName = "자바",
            callTime = "12:12"
        ),
        CallHistory(
            callId = 2,
            profileImage = "https://picsum.photos/200/202",
            nickName = "씨",
            callTime = "12:13"
        ),
    )
    SisoTheme {
        CallHistoryList(
            items = previewList,
            onDelete = {},
            onNavigatePartner = {}
        )
    }
}


@Composable
@Preview
private fun ChatHistoryPreview() {
    val previewList = listOf(
        ChatHistory(
            chatRoomId = 0,
            profileImage = "https://picsum.photos/200/200",
            nickName = "코틀린",
            callTime = "12:10",
            currentMsg = "안녕하세요",
            isNew = true,
        ),
        ChatHistory(
            chatRoomId = 1,
            profileImage = "https://picsum.photos/200/201",
            nickName = "자바",
            callTime = "12:12",
            currentMsg = "안녕하세요",
            isNew = true,
        ),
        ChatHistory(
            chatRoomId = 2,
            profileImage = "https://picsum.photos/200/202",
            nickName = "씨",
            callTime = "12:13",
            currentMsg = "안녕하세요",
            isNew = true,
        ),
    )
    SisoTheme {
        ChatHistoryList(
            items = previewList,
            onDelete = {},
            onNavigateChatRoom = { _, _ -> }
        )
    }
}


@Composable
@Preview
private fun EmptyChatHistory() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_coffeecup),
            contentDescription = "",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(44.dp))
        Text(
            text =
                "아직 대화한 기록이 없어요.\n 좋은 인연과 이야기를 나눠보세요.",
            style = SisoTypoTokens.Body3,
            color = SisoColorTokens.Gray70,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
private fun EmptyCallHistory() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.smartphone),
            contentDescription = "",
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.size(70.dp))
        Text(
            text = "첫 전화를 기다리고 있어요\n마음 맞는 분께 목소리로 인사해보세요.",
            style = SisoTypoTokens.Body3,
            color = SisoColorTokens.Gray70,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun EmptyCallHistoryPreview() {
    SisoTheme {
        EmptyCallHistory()
    }
}

@Composable
@Preview()
private fun SkeletonCallPreview() {
    SkeletonCallRow()
}

@Composable
@Preview()
private fun SkeletonChatPreview() {
    SkeletonChatRow()
}