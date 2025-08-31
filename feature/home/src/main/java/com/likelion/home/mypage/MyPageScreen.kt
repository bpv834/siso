package com.likelion.home.mypage

import android.R.attr.action
import android.util.Log.d
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel<MyPageViewModel>(),
    mainEdit: () -> Unit = {},
    actionSnackbar: () -> Unit = {}
)  {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val progressValue = uiState.progressValue
    val nickname = uiState.nickname
    val age = uiState.age
    val location = uiState.location

    val profileOption = listOf(
        "차단 / 신고한 인연" to {},
        "매칭 필터 설정" to {},
    )

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.size(size = 31.dp))
        Row {
            ProfileCircle(
                padding = 8.dp,
                processFloat = progressValue,
                profileImage = uiState.userImages
            )
            Column(
                modifier = Modifier
                    .offset(x = ((-2).dp))
                    .padding(start = 14.dp, end = 10.dp)
            ) {
                Spacer(modifier = Modifier.size(size = 6.dp))
                Text(
                    text = nickname,
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.Gray90,
                )
                Spacer(modifier = Modifier.size(size = 2.dp))
                Text(
                    text = "${age}세",
                    style = SisoTypoTokens.Title3,
                    color = SisoColorTokens.Gray70,
                )
                Spacer(modifier = Modifier.size(size = 10.dp))
                Row {
                    AsyncImage(
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),
                        model = R.drawable.ic_location,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(size = 2.dp))
                    Text(
                        text = location,
                        style = SisoTypoTokens.Label1,
                        color = SisoColorTokens.Gray90,
                    )
                }

                Spacer(modifier = Modifier.size(size = 11.dp))
                if (progressValue != 1F) {
                    Box(
                        modifier = Modifier
                            .size(width = 190.dp, height = 48.dp)
                            .offset(x = (-18).dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(width = 190.dp, height = 48.dp),
                            model = R.drawable.img_profile_edit,
                            contentDescription = ""
                        )
                        Box(
                            modifier = Modifier
                                .size(width = 190.dp, height = 48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .offset(x = 14.dp)
                                .clickable {
                                    d("click", "click navigateToMyPageMainEdit")
                                    mainEdit() // 수정 화면으로 이동
                                },
                        )
                    }

                } else
                    Spacer(modifier = Modifier.size(width = 190.dp, height = 48.dp))
            }
        }
        Spacer(modifier = Modifier.size(size = 27.dp))


        Spacer(modifier = Modifier.size(size = 19.dp))

        Text(
            text = "계정정보",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.Gray90,
        )

        Spacer(modifier = Modifier.size(size = 12.dp))

        profileOption.forEachIndexed { idx, (text, option) ->
            ProfileText(input = text, option = option)
        }

    }
}



@Composable
fun ProfileCircle(
    padding: Dp,
    processFloat: Float,
    profileImage: String = "",
    completeEdit: ()->Unit = {}
) {
    var progress by remember { mutableFloatStateOf(processFloat) }
    Box(
        modifier = Modifier
            .padding(start = padding)
            .size(128.dp, 153.dp)
    ) { // 박스 우선 순위 아래 부터 그려짐
        // 2 이미지가 다음으로 그러졈
        AsyncImage(
            modifier = Modifier.size(120.dp, 120.dp)
                .clip(CircleShape),
            model = profileImage.ifBlank { R.drawable.example_profile },
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        // 1 서클이 먼저 그려짐
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .size(128.dp)
                .offset(x = (-4).dp, y = (-4).dp),
            color = SisoColorTokens.Gold40,
            strokeWidth = 8.dp,
            trackColor = SisoColorTokens.White.copy(alpha = 0.0F),
            strokeCap = StrokeCap.Round,
        )

        if (processFloat != 1F)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(96.dp, 43.dp)
                    .border(2.dp, SisoColorTokens.Gold40, RoundedCornerShape(99.dp))
                    .background(SisoColorTokens.Gray5, RoundedCornerShape(99.dp))

            ){
                Row(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Spacer(Modifier.size(12.dp))
                    Text(
                        text = "36%",
                        style = SisoTypoTokens.Button1,
                        color = SisoColorTokens.Black,
                        modifier = Modifier
                            .width(41.dp)
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                    Text(
                        text = "완성",
                        style = SisoTypoTokens.Button1,
                        color = SisoColorTokens.Black,
                        modifier = Modifier
                            .width(31.5.dp)
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                    Spacer(Modifier.size(12.dp))
                }

            }
        else
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(117.dp, 44.dp)
                    .border(2.dp, SisoColorTokens.Gold40, RoundedCornerShape(99.dp))
                    .background(SisoColorTokens.Gray5, RoundedCornerShape(99.dp))
                    .clickable {
                        completeEdit()
                    }
            ) {
                Text(
                    text = "수정하기",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Black,
                    modifier = Modifier
                        .size(width = 61.dp, height = 23.dp)
                        .padding(start = 12.dp, top = 8.dp, bottom = 8.dp),
                )
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = rememberAsyncImagePainter(R.drawable.ic_text_edit),
                    tint = SisoColorTokens.Gray70,
                    contentDescription = ""
                )
            }


    }

}

@Composable
fun ProfileText(
    input: String,
    option: () -> Unit
){
    Column (
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .clickable {
                option()
            }
    ){
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = input,
            style = SisoTypoTokens.Body2,
            color = SisoColorTokens.Black,
        )
        Spacer(modifier = Modifier.size(16.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = SisoColorTokens.Gray30
        )
    }


}

@Preview
@Composable
fun MyPageScreenPreview() {
    SisoTheme {
        Surface{
            MyPageScreen()
        }

    }
}