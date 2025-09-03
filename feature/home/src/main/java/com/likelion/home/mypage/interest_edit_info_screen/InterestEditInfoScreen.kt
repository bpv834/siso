package com.likelion.home.mypage.interest_edit_info_screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.util.CoilUtils.result
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@SuppressLint("MutableCollectionMutableState")
@Composable
fun InterestEditInfoScreen(
    viewModel: InterestEditInfoScreenViewModelType,
    popBackStack: (List<String>) -> Unit = {},
) {
    val cultureReceiver by viewModel.cultureReceiverList.collectAsStateWithLifecycle()
    val exerciseReceiver by viewModel.exerciseReceiverList.collectAsStateWithLifecycle()
    val leisureReceiver by viewModel.leisureReceiverList.collectAsStateWithLifecycle()

    val cultureList = viewModel.cultureList

    val exerciseList = viewModel.exerciseList

    val leisureList = viewModel.leisureList



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
                receiverList = cultureReceiver,
                onChipClick = {
                    viewModel.setCultureReceiver(it)
                }
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
                receiverList = exerciseReceiver,
                onChipClick = {
                    viewModel.setExerciseReceiver(it)
                }
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
                receiverList = leisureReceiver
                ,onChipClick = {
                    viewModel.setLeisureReceiver(it)
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
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            CommonActiveButton(
                modifier = null,
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                d("test","${cultureReceiver.size + exerciseReceiver.size
                        + leisureReceiver.size}")
                if (cultureReceiver.size + exerciseReceiver.size
                    + leisureReceiver.size >= 3) {
                    val result = mutableListOf<String>()
                    result.addAll(cultureReceiver)
                    result.addAll(exerciseReceiver)
                    result.addAll(leisureReceiver)
                    popBackStack(result)
                }
            }
            Spacer(Modifier.fillMaxWidth().height(72.dp))

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestEditInfoChips(
    list: List<String>,
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
fun InterestEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            InterestEditInfoScreen(FakeInterestEditInfoScreenViewModel())
        }
    }
}