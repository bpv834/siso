package com.likelion.login.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.likelion.login.state.LoginUiState
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onLoggedIn: () -> Unit = {}
) {
    val viewModel: LoginScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
//    LoginScreen2(
//
//    )

    LoginScreen(
        uiState = uiState.value,
        onLogin = { viewModel.login() },
        onLoggedIn = onLoggedIn
    )
}

// ㅅ테스트
@Composable
fun LoginScreen2(
) {
    val url = "https://589b7097c070.ngrok-free.app/oauth2/authorization/kakao"
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}


@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLogin: () -> Unit,
    onLoggedIn: () -> Unit
) {
    LaunchedEffect(uiState.kakaoToken) {
        if (!uiState.kakaoToken.isNullOrEmpty()) {
            Log.d("LoginS", uiState.kakaoToken)
            onLoggedIn()
        }
    }
    //val loginStatusInfoTitle = if (isLoggedIn.value) "로그인 상태" else "로그아웃 상태"

    val context = LocalContext.current  // ✅ Context 가져오기
    getKeyHash(context)
    Scaffold { innerPadding ->
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = R.drawable.bg,
            contentDescription = "바탕화면"
        )

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 54.dp, top = 120.dp),
                    text = "지금도,\n사랑하기 딱 좋은 나이",
                    style = SisoTypoTokens.H1,
                    color = SisoColorTokens.Orange100
                )
                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 207.dp, top = 8.dp),
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
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 145.dp, top = 601.dp)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        onLogin()
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
            onLogin = {},
            onLoggedIn = {}
        )
    }
}