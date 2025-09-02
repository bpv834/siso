package com.likelion.login.login_start


import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.likelion.domain.login.model.UserStatus
import com.likelion.login.event.LoginEvent
import com.likelion.login.state.LoginUiState
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import timber.log.Timber


@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onInput: () -> Unit = {},
    onHome: () -> Unit = {},
    onKakaoLogin: () -> Unit = {},
    onLogin: () -> Unit,
) {
    val viewModel: LoginScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    val navigatedToInput = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState.value.userState) {
        Timber.tag("유저상태").d("${uiState.value.userState}")
        when (uiState.value.userState) {
            UserStatus.LOGIN -> {
                if (uiState.value.userState == UserStatus.LOGIN && uiState.value.hasProfile) {
                    onHome()
                } else {
                    onInput()
                }
                navigatedToInput.value = false
            }

            UserStatus.REGISTER -> {
                if (!navigatedToInput.value) {
                    onInput()
                    navigatedToInput.value = true
                }
            }

            UserStatus.NONE -> {
                viewModel.clearToken()
                navigatedToInput.value = false
            }
        }
    }
    LoginScreen(
        uiState = uiState.value,
        onInput = onInput,
        onKakaoLogin = { viewModel.handleEvent(LoginEvent.ClickLogin) },
        onLogin = onLogin,
        onHome = onHome
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onInput: () -> Unit, // 입력 화면 이동 (REGISTER일 때)
    onKakaoLogin: () -> Unit, // 카카오 로그인 트리거
    onLogin: () -> Unit,
    onHome: () -> Unit
) {
    Scaffold { innerPadding ->
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = R.drawable.bg,
            contentDescription = "바탕화면"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 24.dp, top = 120.dp),
                    text = "지금도,\n사랑하기 딱 좋은 나이",
                    style = SisoTypoTokens.H1,
                    color = SisoColorTokens.Orange100
                )
                Text(
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                    text = "시팅",
                    style = SisoTypoTokens.H1,
                    fontSize = 72.sp,
                    color = SisoColorTokens.Orange100
                )
            }
            AsyncImage(
                model = R.drawable.kakao_login,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onKakaoLogin()
                    }
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SisoTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onInput = {},
            onKakaoLogin = {},
            onLogin = {},
            onHome = {},

            )
    }
}