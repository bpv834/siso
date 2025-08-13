package com.likelion.login.LastLoginInfoPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun LastLoginInfoScreen(viewModel: LastLoginInfoScreenViewModelType) {
    Column {
        Spacer(modifier = Modifier.size(31.dp))
        Text(
            "내 정보 입력을 완료하였습니다!\n" +
                    "시팅에서 나와 취향이 같은 \n" +
                    "인연을 더 잘 만날 수 있어요",
            style = SisoTypoTokens.Title2,
            color = SisoColorTokens.GrayScale90,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(63.dp))
        AsyncImage(model = R.drawable.img_photo_holder, contentDescription = "")
        CommonActiveButton("인연 만나기",{}, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.size(72.dp))


    }
}

@Preview(showBackground = true)
@Composable
fun LastLoginInfoScreenPreview() {
    SisoTheme {
        LastLoginInfoScreen(FakeLastLoginInfoScreenViewModel())
    }
}
