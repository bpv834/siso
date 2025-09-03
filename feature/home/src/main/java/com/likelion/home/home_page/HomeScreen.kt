package com.likelion.home.home_page


import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.home.home_page.HomeScreenUiEvent.*
import com.likelion.ui.component.card.UserCard
import com.likelion.ui.component.full_screen.FullScreenImageDialog
import com.likelion.ui.theme.SisoColorTokens
import timber.log.Timber

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    // onNavigateToCaller 콜백이 userId와 otherUserId를 인자로 받도록 명시
    onNavigateToCaller: (otherUserId: Long) -> Unit,
    onNavigateToChat: (userId: Long, userNickName: String, chatRoomId: Long) -> Unit,
) {
    // HomeScreen에 viewModel과 onNavigateToCaller 콜백을 그대로 전달
    HomeScreen(
        viewModel = hiltViewModel<HomeScreenViewModel>(),
        toCaller = onNavigateToCaller,
        toChat = onNavigateToChat
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    toCaller: (otherUserId: Long) -> Unit,
    toChat: (userId: Long, userNickName: String, chatRoomId: Long) -> Unit

) {

    // UI State를 Flow에서 collectAsStateWithLifecycle을 사용해 관찰합니다.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // ✅ sideEffect collect
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeScreenSideEffect.NavigateToCaller -> {
                    toCaller(effect.otherUserId)
                }

                is HomeScreenSideEffect.ShowSnackbar -> {
                    // 예시: 스낵바 표시
                    Timber.d("Snackbar: ${effect.message}")
                }
            }
        }
    }


    when (uiState) {
        // 토큰 또는 유저 정보를 로딩 중일 때 로딩 UI를 표시합니다.
        is HomeScreenUiState.LoadingToken, is HomeScreenUiState.LoadingUsers -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // 로딩 스피너
            }
        }

        // 유저 정보 로딩에 성공했을 때
        is HomeScreenUiState.Success -> {
            // Success 상태에서만 users 리스트에 접근합니다.
            val users = (uiState as HomeScreenUiState.Success).users

            val pagerState = rememberPagerState(pageCount = { users.size })

            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) { page ->
                val user = users[page]
                UserCard(
                    user = user,
                    onImageClick = { imageUrl ->
                        selectedImageUrl = imageUrl
                        showImageDialog = true
                    },
                    // 버튼 누르면 UI -> viewModel 이벤트 전달
                    // uiEvent
                    onClickButtonCall = { receiverId ->
                        viewModel.onEvent(
                            OnClickCallButton(
                                receiverId = receiverId
                            )
                        )
                    },
                    toCallScreen = { otherUserId: Long -> toCaller(otherUserId) },
                    onClickMessage = { userId: Long, userNickName: String, chatRoomId: Long ->
                        toChat(userId, userNickName, chatRoomId)
                    },
                    isPossibleMessage = true,
                    imgList = emptyList()
                    )
            }
        }

        // 에러 상태일 때 에러 메시지를 표시합니다.
        is HomeScreenUiState.Error -> {
            val errorMessage = (uiState as HomeScreenUiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "오류 발생: $errorMessage", color = Color.Red)
            }
        }
    }

    if (showImageDialog && selectedImageUrl != null) {
        FullScreenImageDialog(
            imageUrl = selectedImageUrl!!,
            onDismissRequest = {
                showImageDialog = false
                selectedImageUrl = null
            }
        )
    }
}

/*@Preview
@Composable
fun HomeScreenPreview() {
    SisoTheme {
        val usecase = GetAllUsersUseCase(FakeUsersRepositoryImpl())
        HomeScreen(
            viewModel = FakeHomeScreenViewModel(usecase),
            toCaller = {  otherUserId -> })
            toChat = { userId, userNickName, chatRoomId -> })
    }
}*/

