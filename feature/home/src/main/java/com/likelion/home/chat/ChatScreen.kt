package com.likelion.home.chat

import android.view.View
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
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
    onNavigateChatRoom: (String) -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreen(
        viewModel = viewModel,
        onNavigateAlarm = { onNavigateAlarm() },
        onNavigateChatRoom = { nickname -> onNavigateChatRoom(nickname) }
    )
}

@Composable
fun ChatScreen(
    onNavigateAlarm: () -> Unit,
    onNavigateChatRoom: (String) -> Unit,
    viewModel: ChatViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier.background(SisoColorTokens.Gray5)
    ) {
        val pagerState = rememberPagerState(pageCount = { 2 })
        val scope = rememberCoroutineScope()
        LaunchedEffect(
            pagerState.currentPage == 1 &&
                    uiState.chatHistory.isEmpty() && !uiState.isChatHistoryLoading
        ) {
            viewModel.handleEvent(ChatEvent.LoadChatHistory)
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
                        onDelete = { id -> viewModel.handleEvent(ChatEvent.RemoveCallHistory(id)) }
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
    onNavigateChatRoom: (String) -> Unit,
    onDelete: (Long) -> Unit
) {
    if (isLoading) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(8) {
                SkeletonChatRow()
            }
        }
    } else {
        ChatHistoryList(
            items = items,
            onDelete = onDelete,
            onNavigateChatRoom = onNavigateChatRoom
        )
    }
}


@Composable
fun CallHistoryPage(
    items: List<CallHistory>,
    isLoading: Boolean,
    onDelete: (Long) -> Unit
) {
    if (isLoading) {
        // 로딩 중엔 데이터와 무관한 고정 개수 스켈레톤
        LazyColumn(Modifier.fillMaxSize()) {
            items(8) { SkeletonCallRow() }
        }
    } else {
        // 로딩 종료 후 실제 리스트
        CallHistoryList(
            items = items,
            onDelete = onDelete
        )
    }
}

@Composable
fun ChatHistoryList(
    items: List<ChatHistory>,
    onDelete: (Long) -> Unit,
    onNavigateChatRoom: (String) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(items) { _, contact ->
            SwipeableItemWithActions(
                isRevealed = contact.isDelete,
                actions = {
                    ActionIcon(
                        onClick = { onDelete(contact.id) },
                        backgroundColor = SisoColorTokens.Red60,
                        modifier = Modifier.fillMaxHeight()
                    )
                },
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            onNavigateChatRoom(contact.nickName)
                        }
                ) {
                    AsyncImage(
                        model =
                            contact.profileImage,
                        contentDescription = "",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier
                            .padding(vertical = 13.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = contact.nickName,
                            style = SisoTypoTokens.SubTitle1,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = contact.currentMsg,
                            style = SisoTypoTokens.Body4,
                            color = SisoColorTokens.Gray50,
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier
                            .padding(vertical = 13.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = contact.callTime,
                            style = SisoTypoTokens.SubTitle1,
                            modifier = Modifier
                        )
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

@Composable
private fun CallHistoryList(
    items: List<CallHistory>,
    onDelete: (Long) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(items) { _, contact ->
            SwipeableItemWithActions(
                isRevealed = contact.isDelete,
                onExpanded = { /* 필요시 구현 */ },
                onCollapsed = { },
                actions = {
                    ActionIcon(
                        onClick = { onDelete(contact.id) },
                        backgroundColor = SisoColorTokens.Red60,
                        modifier = Modifier.fillMaxHeight()
                    )
                },
            ) {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp)
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
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(width = 120.dp, height = 80.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "인연끊기",
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
            profileImage = "https://picsum.photos/200/200",
            nickName = "코틀린",
            callTime = "12:10"
        ),
        CallHistory(
            profileImage = "https://picsum.photos/200/201",
            nickName = "자바",
            callTime = "12:12"
        ),
        CallHistory(
            profileImage = "https://picsum.photos/200/202",
            nickName = "씨",
            callTime = "12:13"
        ),
    )
    SisoTheme {
        CallHistoryList(
            items = previewList,
            onDelete = {}
        )
    }
}


@Composable
@Preview
private fun ChatHistoryPreview() {
    val previewList = listOf(
        ChatHistory(
            profileImage = "https://picsum.photos/200/200",
            nickName = "코틀린",
            callTime = "12:10",
            currentMsg = "안녕하세요",
            isNew = true,
        ),
        ChatHistory(
            profileImage = "https://picsum.photos/200/201",
            nickName = "자바",
            callTime = "12:12",
            currentMsg = "안녕하세요",
            isNew = true,
        ),
        ChatHistory(
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
            onNavigateChatRoom = { _ -> }
        )
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