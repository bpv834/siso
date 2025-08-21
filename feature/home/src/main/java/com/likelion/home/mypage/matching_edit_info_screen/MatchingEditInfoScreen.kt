package com.likelion.home.mypage.matching_edit_info_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.home.mypage.interest_edit_info_screen.InterestEditInfoScreen
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun MatchingEditInfoScreen(
    viewModel: MatchingEditInfoScreenViewModelType
) {
    val matchingReceiverList = remember {
        mutableStateListOf<String>()
    }

    LaunchedEffect(viewModel.receiverList.collectAsStateWithLifecycle()) {
        matchingReceiverList.addAll(viewModel.receiverList.value)
    }
    val matchingList = remember {
        mutableListOf(
            "#동호회활동\uD83D\uDC65",
            "#봉사활동\uD83E\uDD1D",
            "#취미모임\uD83C\uDFAF",
            "#문화생활\uD83C\uDFAD",
            "#함께운동\uD83C\uDFCB\uFE0F\u200D♂\uFE0F",
            "#산책동행\uD83D\uDEB6\u200D♀\uFE0F",
            "#맛집탐방\uD83C\uDF7D\uFE0F",
            "#차한잔☕\uFE0F",
            "#여행동행\uD83C\uDF0D",
            "#사진동행\uD83D\uDCF7",
            "#골프동반⛳\uFE0F",
            "#영화동행\uD83C\uDF9E\uFE0F",
            "#콘서트동행\uD83C\uDFA4",
            "#전시회동행\uD83D\uDDBC\uFE0F",
            "#등산메이트\uD83E\uDD7E",
            "#자전거메이트\uD83D\uDEB4\u200D♂\uFE0F",
            "#독서모임\uD83D\uDCD6",
            "#토크모임\uD83D\uDCAC",
            "#취향공유\uD83D\uDC8C",
            "#새로운인연\uD83C\uDF1F",
            "#소통해요\uD83D\uDCF1",
            "#함께하는시간⏳",
            "#좋은사람과함께\uD83D\uDE0A",
            "#인연만들기\uD83D\uDC9E"
        )
    }

    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            // 제목과 스크롤 겸치는 만큼 추가 패딩 32 + 109
            Spacer(Modifier.size((141).dp))
            MatchingEditInfoChips(
                list = matchingList,
                receiverList = matchingReceiverList
            )
            // 버튼과 스크롤 겸치는 만큼 추가 패딩 22 + 126
            Spacer(Modifier.size(148.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.TopStart)
                .background(SisoColorTokens.White)
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier.height(31.dp).fillMaxWidth(),
                text = "이런 인연을 만나고 싶어요",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.GrayScale90
            )
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.height(54.dp).fillMaxWidth(),
                text = "최소 3개 이상 선택해주세요\n많이 고를수록 매칭 확률이 높아져요",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale60
            )
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(SisoColorTokens.White),
        ) {
            CommonActiveButton(
                modifier = Modifier
                    .height(54.dp),
                text = "완료하기"
            ) {
                // 선택된 값을 보냄

            }
            Spacer(Modifier.fillMaxWidth().height(72.dp))

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatchingEditInfoChips(
    list: MutableList<String>,
    receiverList: SnapshotStateList<String>
){
    Spacer(Modifier.size(12.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ){
        list.forEach {text->
            Box(
                modifier = Modifier.padding(end = 12.dp,bottom = 12.dp)
            ) {
                CommonChip(
                    text = text,
                    isSelected = receiverList.contains(text),
                ) {
                    if (receiverList.contains(text)) {
                        receiverList.remove(text)
                    } else {
                        receiverList.add(text)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MatchingEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            MatchingEditInfoScreen(FakeMatchingEditInfoScreenViewModel())
        }
    }
}