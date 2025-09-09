package com.likelion.home.home_page


import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.ui.R
import com.likelion.home.home_page.HomeScreenUiEvent.*
import com.likelion.ui.component.card.UserCard
import com.likelion.ui.component.full_screen.FullScreenImageDialog
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import timber.log.Timber

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    // onNavigateToCaller 콜백이 userId와 otherUserId를 인자로 받도록 명시
    onNavigateToCaller: (otherUserId: Long) -> Unit,
    onNavigateToChat: (userId: Long, userNickName: String, chatRoomId: Long) -> Unit,
    onNavigateToEditProfile: () -> Unit
) {
    // HomeScreen에 viewModel과 onNavigateToCaller 콜백을 그대로 전달
    HomeScreen(
        viewModel = hiltViewModel<HomeScreenViewModel>(),
        toCaller = onNavigateToCaller,
        toChat = onNavigateToChat,
        toEdit = onNavigateToEditProfile,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    toCaller: (otherUserId: Long) -> Unit,
    toChat: (userId: Long, userNickName: String, chatRoomId: Long) -> Unit,
    toEdit: () -> Unit

) {

    // UI State를 Flow에서 collectAsStateWithLifecycle을 사용해 관찰합니다.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateHomePage by viewModel.uiStateHomePage.collectAsState()

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }
    val showHomeDialog by viewModel.showHomeDialog
        .collectAsStateWithLifecycle(initialValue = false)

    var showImageDialog by remember { mutableStateOf(false) }


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
            Timber.d("uiState = $uiState, ")

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

    if (!uiStateHomePage.isDialog) {
        CustomDialog(
            onConfirmClick = {
                viewModel.onEvent(HomeScreenUiEvent.ChangeDialogStatus(true))
                toEdit()
            },
            onDismissClick = {
                viewModel.onEvent(HomeScreenUiEvent.ChangeDialogStatus(true))

            }
        )
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


@Composable
fun CustomDialog(
    onConfirmClick: () -> Unit = {},
    onDismissClick: () -> Unit = {}
) {
    Dialog(onDismissRequest = { onDismissClick() }) {
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.background(SisoColorTokens.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(64.dp))
                Image(
                    painter = painterResource(R.drawable.img_dialog),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(horizontal = 48.dp)
                )
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    text = buildAnnotatedString {
                        append("상세 프로필을 작성하면\n나와 맞는 인연을\n만날 확률이\n")
                        withStyle(style = SpanStyle(color = SisoColorTokens.PrimaryColor)) {
                            append("5배")
                        }
                        append(" 높아져요")
                    }, textAlign = TextAlign.Center,
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray90
                )
                Spacer(modifier = Modifier.size(38.dp))
                Button(
                    onClick = { onConfirmClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "상세 프로필 작성하러 가기",
                        style = SisoTypoTokens.Button1,
                        color = SisoColorTokens.Gray90,

                        )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "다시보지않기",
                    style = SisoTypoTokens.Button1,
                    color = SisoColorTokens.Gray50,
                    modifier = Modifier.clickable { onDismissClick() }
                )
                Spacer(modifier = Modifier.size(38.dp))
            }
        }
    }
}

@Composable
@Preview
fun CustomDialogPreview() {
    SisoTheme {
        CustomDialog()
    }
}
