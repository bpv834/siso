package com.likelion.home.mypage.setting_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingScreen() {
    val settingOption = mutableStateListOf(
        "계정" to {},
        "알림" to {},
        "문의하기" to {},
        "결제 내역 조회" to {},
        "개인정보 처리방침" to {},
        "법적고지" to {},
        "로그아웃" to {},
        "회원탈퇴" to {},
    )
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()

    ) {
        Spacer(Modifier.size(24.dp))

        settingOption.forEachIndexed { idx, (text,option)->
            SettingText(input = text,option = option)
        }
    }
}

@Composable
fun SettingText(
    input: String,
    option: () -> Unit
){
    Column (
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .clickable{
                option()
            }
    ){
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp),
            text = input,
            style = SisoTypoTokens.Body2,
            color = if (input =="회원탈퇴") SisoColorTokens.GrayScale40
            else SisoColorTokens.Black,
        )
        Spacer(modifier = Modifier.size(16.dp))
        if (input != "회원탈퇴") {
            HorizontalDivider(
                thickness = 1.dp,
                color = SisoColorTokens.GrayScale30
            )
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    Surface(
        color = SisoColorTokens.White
    ) {
        SettingScreen()
    }
}
