package com.likelion.home.mypage.setting_screen.call_edit_screen

import android.R.attr.thickness
import android.annotation.SuppressLint
import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingCallEditScreen() {
    val receiverChecked = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        // 최초의 설정 값을 받는 곳
        receiverChecked
    }
    val settingOption = mutableStateListOf(
        "새로운 인연 요청 알림" to {/* view 모델에 값을 전달 */},
        "내 프로필 부재중 알림" to {},
        "마케팅 알림" to {},
    )
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())

    ) {
        Spacer(Modifier.size(24.dp))

        settingOption.forEachIndexed { idx, (text,option)->
            SettingText(input = text,option = option, receiverChecked = receiverChecked)
        }
    }
}

@Composable
fun SettingText(
    input: String,
    option: () -> Unit,
    receiverChecked: MutableList<String> = remember { mutableStateListOf() }
){
    var currentSettingOption by remember {
        mutableStateOf("")
    }
    if(receiverChecked.contains(input)){
        currentSettingOption = input
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()

    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(3.dp))
                .padding(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f)
                    .height(28.dp),
                text = input,
                style = SisoTypoTokens.Body2,
                color = SisoColorTokens.Black,
            )

            Switch(
                modifier = Modifier.weight(0.2f),
                checked = input == currentSettingOption,
                onCheckedChange = {
                    d("currentSettingOption", "before $currentSettingOption")
                    if (currentSettingOption == input) {
                        currentSettingOption = ""
                    } else {
                        currentSettingOption = input
                    }
                    d("currentSettingOption", "after $currentSettingOption")
                    option()
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = SisoColorTokens.Gold40,
                    uncheckedTrackColor = SisoColorTokens.GrayScale50,
                    checkedBorderColor = SisoColorTokens.GrayScale30.copy(alpha = 0.0f),
                    checkedThumbColor = SisoColorTokens.White,
                    uncheckedThumbColor = SisoColorTokens.White,
                    uncheckedBorderColor = SisoColorTokens.GrayScale30.copy(alpha = 0.0f),
                ),
                thumbContent = {
                    Box(
                        modifier =
                            Modifier
                                .padding(2.dp), // 패딩 으로 check된 상태와 동일 한 크기를 유지
                        contentAlignment = Alignment.Center
                    ) {

                    }

                }
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = SisoColorTokens.GrayScale30
        )

    }
}

@Preview
@Composable
fun SettingCallEditScreenPreview() {
    Surface(
        color = SisoColorTokens.White
    ) {
        SettingCallEditScreen()
    }
}