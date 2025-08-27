package com.likelion.home.mypage.additional_info.additional_info_religion_screen

import android.annotation.SuppressLint
import android.util.Log.d
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.home.navigation.Pub
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInfoReligionScreen(
    viewModel: AdditionalInfoReligionScreenViewModelType,
    popBackStack: (String) -> Unit = {},
) {
    val receiver by viewModel.receiver.collectAsStateWithLifecycle()

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

    var selectText by remember {
        mutableStateOf("")
    }

    val editText = remember {
        mutableStateOf("")
    }

    var bottomState by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier
                    .height(31.dp)
                    .fillMaxWidth(),
                text = receiver.ifBlank { "종교가 있나요?" },
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
                        isSelected = selectText == text,
                    ) {
                        if(text == "기타(직접입력)") {
                            //입력창 오픈
                            bottomState = true
                        }else {
                            if (selectText == text) {
                                selectText = ""
                            } else {
                                selectText = text
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(366.dp))
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CommonActiveButton(
                modifier = null,
                text = "완료하기"
            ) {
                // 선택된 값을 보냄
                viewModel.updateReceiver(receiver)
                { religion->
                    popBackStack(religion)
                }

            }
            Spacer(Modifier.size(72.dp))
        }


        if (bottomState) {
            BottomRegion(
                sheetState = sheetState,
                editText = editText,
                onDismiss = {
                    // 바텀 내리기
                    bottomState = false
                    // 입력창 초기화
                    editText.value = ""
                },
                complete = {
                    // 뷰에 추가
                    religionList.add(religionList.size - 1, editText.value)
                    // 해당 종교 선택
                    selectText = editText.value
                    // 입력창 초기화
                    editText.value = ""
                    // 바텀 내리기
                    bottomState = false
                }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomRegion(
    sheetState : SheetState,
    editText : MutableState<String>,
    onDismiss:()->Unit = {},
    complete:()->Unit = {}
){
    ModalBottomSheet(
        modifier = Modifier
            .systemBarsPadding(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        onDismissRequest = {

            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Spacer(Modifier.size(34.dp))
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
                    onDismiss()
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

        Spacer(Modifier.size(37.dp))
        CommonOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = editText.value,
            onValueChange = {
                editText.value = it
            },
            placeholderText = "종교를 입력해주세요"
        )
        CommonActiveButton(
            text = "완료",
        ) {
            if (editText.value.isNotBlank()) {
                complete()
            }
        }
    }
}