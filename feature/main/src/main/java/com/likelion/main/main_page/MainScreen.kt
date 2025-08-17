package com.likelion.main.main_page

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.domain.repository.FakeUsersRepository2Impl
import com.likelion.domain.usecase.GetAllUsersUseCase
import com.likelion.ui.component.card.UserCard
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainScreenViewModelType) {
    val userList by viewModel.userList.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    // 팝업 상태를 관리하는 변수 추가
    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // 1️⃣ Custom FlingBehavior를 적용
    val flingBehavior = rememberSnapFlingBehavior(listState)

    // ✅ 수정된 코드: 화면 중앙에 가장 가까운 아이템의 인덱스를 가져옴
    val currentIndex by remember {
        derivedStateOf {
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                return@derivedStateOf 0
            }

            // 화면 중앙점 계산
            val viewportCenter = listState.layoutInfo.viewportSize.height / 2f

            // 보이는 아이템 중 화면 중앙에 가장 가까운 아이템을 찾음
            val closestItem = visibleItems.minByOrNull { item ->
                // 각 아이템의 중앙점과 화면 중앙점 사이의 거리를 계산
                val itemCenter = item.offset + item.size / 2f
                abs(itemCenter - viewportCenter)
            }

            closestItem?.index ?: 0
        }
    }
    val currentUser = userList.getOrNull(currentIndex)

    Scaffold(
        bottomBar = {}
    ) { innerPadding ->  // ✅ Scaffold가 제공하는 paddingValues
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // ✅ 여기서 적용
        ) {
            // 배경 이미지
            currentUser?.userImages?.firstOrNull()?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.6f) // 투명도 60% (0.6f) 적용
                        .then(
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                // Android 12 (API 31) 이상일 경우 RenderEffect 적용
                                Modifier.graphicsLayer {
                                    renderEffect = RenderEffect
                                        .createBlurEffect(
                                            60f, // 블러 반경 (픽셀)
                                            60f, // 블러 반경 (픽셀)
                                            Shader.TileMode.CLAMP
                                        )
                                        .asComposeRenderEffect()
                                }
                            } else {
                                // API 30 이하일 경우 빈 Modifier 반환 (블러 효과 없음)
                                Modifier
                            }
                        )
                )
            }
            // ✅ 어두운 오버레이 추가
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = SisoColorTokens.Black.copy(alpha = 0.6f))
            )

            // 콘텐츠
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 73.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // 이 부분을 추가하세요,
                flingBehavior = flingBehavior
            ) {
                items(userList) { user ->
                    UserCard(
                        user = user,
                        onImageClick = { imageUrl ->
                            selectedImageUrl = imageUrl
                            showImageDialog = true
                        },
                    )
                }
            }
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



}

@Composable
fun FullScreenImageDialog(
    imageUrl: String,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        // ✅ 이 속성으로 기본 너비를 무시하고 전체 화면 사용 가능
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .width(328.dp).height(578.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "확대된 이미지",
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.FillBounds,

            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    SisoTheme {
        val usecase = GetAllUsersUseCase(FakeUsersRepository2Impl())
        MainScreen(viewModel = FakeMainScreenViewModel(usecase))
    }
}