package com.likelion.home.mypage.matching_edit_info_screen

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    viewModel: MatchingEditInfoScreenViewModelType,
    popBackStack: (List<String>) -> Unit = {},
) {
    val matchingReceiverList by viewModel.receiverList.collectAsStateWithLifecycle()

    val matchingList = remember {
        mutableListOf(
            "#동호회활동",
            "#봉사활동",
            "#취미모임",
            "#문화생활",
            "#함께운동",
            "#산책동행",
            "#맛집탐방",
            "#차한잔",
            "#여행동행",
            "#사진동행",
            "#골프동반",
            "#영화동행",
            "#콘서트동행",
            "#전시회동행",
            "#등산메이트",
            "#자전거메이트",
            "#독서모임",
            "#토크모임",
            "#취향공유",
            "#새로운인연",
            "#소통해요",
            "#함께하는시간",
            "#좋은사람과함께",
            "#인연만들기"
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
                receiverList = matchingReceiverList,
                onChipClick = {
                    d("addString","onChipClick$it")
                    viewModel.addString(it)
                }
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
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.height(54.dp).fillMaxWidth(),
                text = "최소 3개 이상 선택해주세요\n많이 고를수록 매칭 확률이 높아져요",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray60
            )
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth(),
        ) {
            CommonActiveButton(
                modifier = null,
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                if (matchingReceiverList.size >= 3) {
                    popBackStack(matchingReceiverList)
                }
            }
            Spacer(Modifier.fillMaxWidth().height(72.dp))

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatchingEditInfoChips(
    list: MutableList<String>,
    receiverList: MutableList<String>,
    onChipClick: (String) -> Unit = {}
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
                    onChipClick(text)
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