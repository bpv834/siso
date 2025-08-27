package com.likelion.login.login_start


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
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
import java.security.MessageDigest


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
                if (uiState.value.userState == UserStatus.LOGIN /*&& uiState.value.user != null*/) {
                    Timber.d("onHome()")
                    onHome()
                }
                navigatedToInput.value = false
            }//onHome() // 홈 화면 이동
            UserStatus.REGISTER -> {
                Timber.d("REGISTER()")
                if (!navigatedToInput.value) {
                    onInput()
                    navigatedToInput.value = true
                }
            }

            UserStatus.NONE -> {
                Timber.d("NONE()")
                Timber.d("uiST: ${uiState.value}()")
                navigatedToInput.value = false
            } //
            else -> {
                Timber.d("ELSE()")
                Timber.d("ELSE: ${uiState.value}()")

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
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            ) {
                Text("초기화", style = SisoTypoTokens.Button2)
            }
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            ) {
                Text("이미회원이라면", style = SisoTypoTokens.Button2)
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

@SuppressLint("PackageManagerGetSignatures")
fun getKeyHash(context: Context): String? {
    return try {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )
        } else {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
        }

        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.signingInfo!!.apkContentsSigners
        } else {
            @Suppress("DEPRECATION")
            packageInfo.signatures
        }

        if (signatures != null) {
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val keyHash = android.util.Base64.encodeToString(
                    md.digest(),
                    android.util.Base64.NO_WRAP
                )
                Log.d("키해시", "앱의 디버그 키해시는 👉 $keyHash") // ✅ 한글 로그
                return keyHash
            }
        }
        null
    } catch (e: Exception) {
        Log.e("키해시", "키해시 생성 중 오류 발생: ${e.message}") // ✅ 한글 에러 로그
        null
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