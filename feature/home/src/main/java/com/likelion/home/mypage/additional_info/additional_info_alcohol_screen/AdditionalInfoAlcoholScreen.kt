package com.likelion.home.mypage.additional_info.additional_info_alcohol_screen

import android.util.Log.d
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun AdditionalInfoAlcoholScreen(
    viewModel: AdditionalInfoAlcoholScreenViewModelType,
    popBackStack: () -> Unit = {},
) {
    var receiver by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel.receiver.collectAsStateWithLifecycle()) {
        receiver = viewModel.receiver.value
    }
    val smokingList = listOf(
        "자주 마셔요 (주 3회이상)",
        "가끔 마셔요 (주 1회~한 달에 한 번)",
        "전혀 안 해요",
    )

    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier.height(62.dp).fillMaxWidth(),
                text = "음주 습관을 알려주세요. \n" +
                        "서로 이해하는 데 도움이 돼요",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(13.dp))
            Text(
                modifier = Modifier.height(27.dp).fillMaxWidth(),
                text = "최소 1개 이상 선택해주세요",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray60
            )
            Spacer(Modifier.size(32.dp))
            smokingList.forEach { text ->
                CommonChip(
                    text = text,
                    isSelected = receiver == text,
                ) {
                    receiver = if (receiver == text) {
                        ""
                    } else {
                        text
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
            // 스크롤을 위한 패딩
            Spacer(modifier = Modifier.size(242.dp))
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CommonActiveButton(
                modifier = null,
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                if (receiver.isNotBlank()) {
                    d("receiver", receiver)
                }
                popBackStack()
            }
            Spacer(Modifier.size(72.dp))
        }
    }
}

@Preview
@Composable
fun AdditionalInfoAlcoholScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            AdditionalInfoAlcoholScreen(viewModel = FakeAdditionalInfoAlcoholScreenViewModel())
        }
    }
}