package com.likelion.home.chat

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun PartnerProfileScreenRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel()
) {
    PartnerProfileScreen(
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerProfileScreen(
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(painter = painterResource(R.drawable.ic_back), contentDescription = "")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = R.drawable.img_example_profile, contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(358.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
            Spacer(modifier = Modifier.size(24.dp))
            Row {
                Icon(painter = painterResource(R.drawable.ic_location), contentDescription = "지역 아이콘")
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "서울 중구", style = SisoTypoTokens.SubTitle1, color = SisoColorTokens.Gray90,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(text = "닉네임, 56세", style = SisoTypoTokens.Title2, color = SisoColorTokens.Gray90)
            Spacer(modifier = Modifier.size(12.dp))
            Image(
                painter = painterResource(R.drawable.voice_sample),
                contentDescription = "음성 파일",
                modifier = Modifier
                    .width(86.dp)
                    .height(44.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                Modifier.horizontalScroll(rememberScrollState())
            ) {
                ProfileChip("음악감상")
                ProfileChip("등산")
                ProfileChip("산책")
                ProfileChip("도박")
                ProfileChip("낚시")
            }
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = "안녕하세요. 인생의 황혼기에 접어들었지만, 늘 새로운 경험과 사랑을 찾아 나아가고 있습니다. 서로를 이해하며 함께할 수 있는 분을 기다립니다.",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray90
            )
            Spacer(modifier = Modifier.size(48.dp))
            Text(
                "기본 정보",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray90
            )
            Spacer(modifier = Modifier.size(16.dp))
            ProfileInfoBox("나이", "56세")
            ProfileInfoBox("사는 곳", "서울 중구")
            Column {
                Text(
                    text = "성별",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Gray50
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "여성",
                    style = SisoTypoTokens.Body2,
                    color = SisoColorTokens.Gray90
                )
            }
            Spacer(modifier = Modifier.size(48.dp))
            Text(
                "추가 정보",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray90
            )
            Spacer(modifier = Modifier.size(16.dp))
            ProfileInfoBox("종교", "기독교(개신교)")
            ProfileInfoBox("흡연", "비흡연자")
            ProfileInfoBox("음주", "가끔 마셔요 (주 1회~한 달에 한 번)")
            ProfileInfoBox("MBTI", "ISFJ")
            Spacer(modifier = Modifier.size(60.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(color = SisoColorTokens.PrimaryColor, shape = CircleShape)
            ) {
                Text(text = "전화걸기", style = SisoTypoTokens.Button1, color = SisoColorTokens.Gray90)
            }
        }
    }
}

@Composable
@Preview
fun PartnerProfileScreenPreview() {
    SisoTheme {
        PartnerProfileScreen(
            onNavigateUp = {}
        )
    }
}

@Composable
fun ProfileChip(
    body: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 6.dp)
            .background(color = SisoColorTokens.Gray20, shape = CircleShape)
    ) {
        Text(
            text = "#${body}",
            style = SisoTypoTokens.Button1,
            color = SisoColorTokens.Gray90,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.5.dp)
        )
    }
}

@Composable
fun ProfileInfoBox(
    title: String,
    input: String
) {
    Column {
        Text(
            text = title,
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.Gray50
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = SisoColorTokens.Gray20, shape = CircleShape)
                .padding(end = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = input,
                style = SisoTypoTokens.Label1,
                color = SisoColorTokens.Gray90,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.size(24.dp))

    }
}