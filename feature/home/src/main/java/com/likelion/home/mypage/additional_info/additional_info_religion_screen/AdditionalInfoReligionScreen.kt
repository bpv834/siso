package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInfoReligionScreen(
    viewModel: AdditionalInfoReligionScreenViewModelType,
    popBackStack: () -> Unit = {},
) {
    val receiverList = remember {
        mutableStateListOf<String>()
    }

    LaunchedEffect(viewModel.receiverList.collectAsStateWithLifecycle()) {
        receiverList.addAll(viewModel.receiverList.value)
    }
    val religionList = remember {
        mutableListOf(
            "기독교(개신교)",
            "불교",
            "가톨릭",
            "원불교",
            "무교",
            "기타(직접입력)"
        )
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var editText by remember {
        mutableStateOf("")
    }

    var bottomState by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier.height(31.dp).fillMaxWidth(),
                text = "종교가 있나요?",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(32.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ){
                religionList.forEach {text->
                    CommonChip(
                        text = text,
                        isSelected = receiverList.contains(text),
                    ) {
                        if(text == "기타(직접입력)") {
                            //입력창 오픈
                            bottomState = true
                        }else {
                            if (receiverList.contains(text)) {
                                receiverList.remove(text)
                            } else {
                                receiverList.add(text)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(372.dp))
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CommonActiveButton(
                modifier = Modifier
                    .height(54.dp),
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                //viewModel.updateReceiverList(receiverList)
                popBackStack()
            }
            Spacer(Modifier.size(72.dp))
        }


        if (bottomState) {
            ModalBottomSheet(
                modifier = Modifier.systemBarsPadding(),
                onDismissRequest = {
                    // 바텀 내리기
                    bottomState = false
                    // 입력창 초기화
                    editText = ""
                },
                sheetState = sheetState,
                dragHandle = null
            ) {
                Spacer(Modifier.size(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(31.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.height(31.dp),
                        text = "종교 입력",
                        style = SisoTypoTokens.Title2,
                        color = SisoColorTokens.Black,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .align(Alignment.CenterEnd),
                        onClick = {
                            // 바텀 내리기
                            bottomState = false
                            // 입력 텍스트 초기화
                            editText = ""
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_bottom_close),
                            tint = SisoColorTokens.Gray90,
                            contentDescription = ""
                        )
                    }
                }
                Spacer(Modifier.size(31.dp))
                Row {
                    CommonOutlinedTextFiled(
                        modifier = Modifier.fillMaxWidth(0.8F),
                        value = editText,
                        onValueChange = {
                            editText = it
                        },
                        placeholderText = "종교를 입력해주세요"
                    )
                    CommonChip(
                        text = "완료",
                        isSelected = true,
                    ) {
                        if (editText.isNotBlank()) {
                            // 뷰에 추가
                            religionList.add(religionList.size - 1, editText)
                            // 해당 종교 선택
                            receiverList.add(editText)
                            // 입력창 초기화
                            editText = ""
                            // 바텀 내리기
                            bottomState = false
                        }
                    }
                }


            }
        }
    }
}

@Preview
@Composable
fun AdditionalInfoReligionScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            AdditionalInfoReligionScreen(FakeAdditionalInfoReligionScreenViewModel())
        }
    }
}