package com.likelion.home.mypage.interest_edit_info_screen

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
fun InterestEditInfoScreen(
    viewModel: InterestEditInfoScreenViewModelType,
    popBackStack: () -> Unit = {},
) {
    val cultureReceiverList = remember {
        mutableStateListOf<String>()
    }
    val exerciseReceiverList = remember {
        mutableStateListOf<String>()
    }
    val leisureReceiverList = remember {
        mutableStateListOf<String>()
    }

    LaunchedEffect(viewModel.receiverList.collectAsStateWithLifecycle()) {
        cultureReceiverList.addAll(viewModel.receiverList.value)
    }
    val cultureList = remember {
        mutableListOf(
            "#음악감상",
            "#사진촬영",
            "#서예",
            "#글쓰기",
            "#영화감상",
            "#전시관람",
            "#클래식감상",
            "#노래부르기",
            "#댄스"
        )
    }

    val exerciseList = remember {
        mutableListOf(
            "#등산",
            "#낚시",
            "#요가",
            "#골프",
            "#자전거",
            "#캠핑",
            "#수영",
            "#바둑",
            "#볼링",
            "#탁구",
            "#꽃꽂이",
            "#드라이브"
        )
    }

    val leisureList = remember {
        mutableListOf(
            "#독서",
            "#베이킹",
            "#뜨개질",
            "#원예",
            "#여행",
            "#맛집",
            "#명상",
            "#와인",
            "#요리",
            "#탁구",
            "#인테리어",
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

            Spacer(Modifier.size((109+32).dp))
            Text(
                modifier = Modifier.height(23.dp).fillMaxWidth(),
                text = "문화 & 예술",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50
            )
            InterestEditInfoChips(
                list = cultureList,
                receiverList = cultureReceiverList
            )
            Spacer(Modifier.size(12.dp))
            Text(
                modifier = Modifier.height(23.dp).fillMaxWidth(),
                text = "운동 & 야외활동",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50
            )
            InterestEditInfoChips(
                list = exerciseList,
                receiverList = exerciseReceiverList
            )
            Spacer(Modifier.size(12.dp))
            Text(
                modifier = Modifier.height(23.dp).fillMaxWidth(),
                text = "여가 & 취미",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50
            )
            InterestEditInfoChips(
                list = leisureList,
                receiverList = leisureReceiverList
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
                text = "나의 관심을 선택해주세요",
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
                .fillMaxWidth()
                .background(SisoColorTokens.White),
        ) {
            CommonActiveButton(
                modifier = Modifier
                    .height(54.dp),
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                popBackStack()
            }
            Spacer(Modifier.fillMaxWidth().height(72.dp))

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestEditInfoChips(
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
fun InterestEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            InterestEditInfoScreen(FakeInterestEditInfoScreenViewModel())
        }
    }
}