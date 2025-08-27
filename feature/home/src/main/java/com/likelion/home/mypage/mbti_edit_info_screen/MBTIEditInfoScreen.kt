package com.likelion.home.mypage.mbti_edit_info_screen

import android.R.attr.text
import android.R.id.input
import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.AdditionalInfoSmokingScreen
import com.likelion.home.mypage.additional_info.additional_info_smoking_screen.FakeAdditionalInfoSmokingScreenViewModel
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun MBTIEditInfoScreen(
    viewModel: MBTIEditInfoScreenViewModelType,
    popBackStack: () -> Unit = {},
) {
    var receiver by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel.receiver.collectAsStateWithLifecycle()) {
        receiver = viewModel.receiver.value
    }
    val navbarBottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val receiverList = remember { if (receiver.isNotBlank())receiver.split("").toMutableStateList()
    else mutableStateListOf("", "", "", "") }
    val exInList = listOf(
        "E" to "에너지를 \n사람 만나서 얻는 편",
        "I" to "혼자 있을 때 \n충전되는 편",
    )

    val facImgList = listOf(
        "S" to "눈앞의 사실, \n경험 위주로 보는 편",
        "N" to "앞으로의 가능성, \n큰 그림을 보는 편",
    )

    val logMinList = listOf(
        "T" to "이성적·논리적으로 \n판단하는 편",
        "F" to "사람의 마음·관계까지\n고려하는 편",
    )

    val improPlanList = listOf(
        "P" to "즉흥적이고 유연하게 \n풀어가는 편",
        "J" to "미리 계획을 세우는 걸\n좋아하는 편",
    )

    val MBTIList = listOf(
        exInList,
        facImgList,
        logMinList,
        improPlanList
    )


    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier.height(62.dp).fillMaxWidth(),
                text = "나의 성격유형(MBTI)을 \n" +
                        "선택해 주세요",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(13.dp))
            Text(
                modifier = Modifier.height(108.dp).fillMaxWidth(),
                text = "성격유형(MBTI)은 나의 성격을 16가지로 구분하는 간단한 검사입니다.\n" +
                        "나와 비슷한 성격, 혹은 다른 성격을 가진 사람을 만나보는 데 도움이 될 수 있어요.",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray60
            )
            Spacer(Modifier.size(32.dp))
            MBTIList.forEachIndexed { fourIdx, input ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(111.dp)
                ) {
                    input.forEachIndexed { idx, pair ->
                        val isChecked = if (receiverList[fourIdx].isNotBlank())
                            receiverList[fourIdx] == pair.first
                        else false
                        Column(
                            modifier = Modifier.weight(1f)
                                .background(
                                    if (isChecked) SisoColorTokens.Gold40
                                    else SisoColorTokens.Gray20,
                                    if (idx == 0) RoundedCornerShape(
                                        topStart = 24.dp,
                                        bottomStart = 24.dp
                                    )
                                    else RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                                ).clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ){
                                    receiverList[fourIdx] = pair.first
//                                   receiver = receiverList.joinToString("") {
//                                       if (it.isNotBlank()) it
//                                       else ""
//
//                                    }
                                    d("receiver", receiver)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(Modifier.size(12.dp))
                            Text(
                                modifier = Modifier
                                    .height(29.dp),
                                text = pair.first,
                                textAlign = TextAlign.Center,
                                style = SisoTypoTokens.Title2,
                                color = SisoColorTokens.Gray90
                            )
                            Spacer(Modifier.size(8.dp))
                            Text(
                                modifier = Modifier.padding(start = 14.dp, end = 14.dp)
                                    .height(46.dp),
                                text = pair.second,
                                textAlign = TextAlign.Center,
                                style = SisoTypoTokens.Label1,
                                color = SisoColorTokens.Gray60
                            )
                            Spacer(Modifier.size(16.dp))
                        }
                        if (idx == 0) {
                            VerticalDivider(
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                thickness = 1.dp, color = SisoColorTokens.Gray30
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            // 화면 작아졌을 떄 스크롤을 위한 패딩
            Spacer(Modifier.size(350.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Spacer(Modifier.size(6.dp))
            CommonActiveButton(
                modifier = Modifier
                    .height(54.dp),
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                if (receiver.isNotBlank()) {
                    d("receiver", receiver)

                }
                popBackStack()
            }
            Spacer(Modifier.fillMaxWidth().height(68.dp - navbarBottomPadding)
                .background(SisoColorTokens.Gray5))
        }
    }
}

@Preview
@Composable
fun MBTIEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            MBTIEditInfoScreen(viewModel = FakeMBTIEditInfoScreenViewModel())
        }
    }
}