package com.likelion.home.home_page


import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.domain.home.repository.FakeUsersRepositoryImpl
import com.likelion.domain.home.usecase.GetAllUsersUseCase
import com.likelion.ui.component.card.UserCard
import com.likelion.ui.component.full_screen.FullScreenImageDialog
import com.likelion.ui.theme.SisoTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    // onNavigateToCaller 콜백이 userId와 otherUserId를 인자로 받도록 명시
    onNavigateToCaller: (userId: Long, otherUserId: Long) -> Unit
) {
    // HomeScreen에 viewModel과 onNavigateToCaller 콜백을 그대로 전달
    HomeScreen(
        viewModel = hiltViewModel<HomeScreenViewModel>(),
        toCaller = onNavigateToCaller
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModelType,
    toCaller: (userId: Long, otherUserId: Long) -> Unit
) {
    val userList by viewModel.userList.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { userList.size })

    // 팝업 상태를 관리하는 변수 추가
    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // 1️⃣ Custom FlingBehavior를 적용
    // VerticalPager는 자체적으로 스냅 동작을 지원합니다.

    // 콘텐츠
    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) { page ->
        val user = userList[page]
        UserCard(
            user = user,
            onImageClick = { imageUrl ->
                selectedImageUrl = imageUrl
                showImageDialog = true
            },
            onClickButtonCall = { receiverIdId -> viewModel.onClickCallButton(0L, receiverIdId) },
            toCallScreen = { userId: Long, otherUserId: Long -> toCaller(userId, otherUserId) },
            true)
    }

    // ✅ 팝업을 조건부로 표시
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


@Preview
@Composable
fun HomeScreenPreview() {
    SisoTheme {
        val usecase = GetAllUsersUseCase(FakeUsersRepositoryImpl())
        HomeScreen(
            viewModel = FakeHomeScreenViewModel(usecase),
            toCaller = { userId, otherUserId -> })
    }
}

