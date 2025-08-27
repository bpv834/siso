package com.likelion.login.login_end

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun LastLoginInfoScreen(
    onNavigation: () -> Unit,
    viewModel : LastLoginInfoScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.size(31.dp))
        Text(
            "내 정보 입력을 완료하였습니다!\n" +
                    "시팅에서 나와 취향이 같은 \n" +
                    "인연을 더 잘 만날 수 있어요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.Gray90,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.size(63.dp))
        AsyncImage(
            model = com.likelion.ui.R.drawable.img_person,
            contentDescription = "",
            modifier = Modifier
                .height(302.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.size(80.dp))
        CommonActiveButton(
            text = "인연 만나기",
            onClick = {
                viewModel.onClick()
                onNavigation() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.size(72.dp))


    }
}

@Preview
@Composable
fun LastLoginInfoScreenPreview() {
    SisoTheme {
      /*  LastLoginInfoScreen({})*/
    }
}