package com.likelion.home.mypage.setting_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun SettingScreen(
    action : List<() -> Unit> = listOf()
) {
    val settingOption = mutableStateListOf(
        "계정" to {if (action.isNotEmpty()) action[0]()},
        "알림" to {if (action.isNotEmpty()) action[1]()},
        "문의하기" to {if (action.isNotEmpty()) action[2]()},
        "결제 내역 조회" to {if (action.isNotEmpty()) action[3]()},
        "개인정보 처리방침" to {if (action.isNotEmpty()) action[4]()},
        "법적고지" to {if (action.isNotEmpty()) action[5]()},
        "로그아웃" to {if (action.isNotEmpty()) action[6]()},
        "회원탈퇴" to {if (action.isNotEmpty()) action[7]()},
    )
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())

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
