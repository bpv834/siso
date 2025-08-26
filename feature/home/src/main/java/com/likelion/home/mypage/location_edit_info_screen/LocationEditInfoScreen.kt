package com.likelion.home.mypage.location_edit_info_screen

import android.R.attr.text
import com.likelion.ui.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.domain.mypage.repository.FakeLocationRepositoryImpl
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import timber.log.Timber.Forest.d

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationEditInfoScreen(
    viewModel: LocationEditInfoScreenViewModelType,
    popBackStack: (String) -> Unit = {},
) {
    var resultText by remember { mutableStateOf("") }
    val locationTextFloat = if (resultText.isBlank())  0.845F else 1F
    var bottomState by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val topLocations by viewModel.topLocation.collectAsStateWithLifecycle()
    d("topLocations : $topLocations")
    val bottomLocation by viewModel.bottomLocation.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier.height(31.dp),
                text = "어디에 거주하시나요?",
                style = SisoTypoTokens.Title2,
                color = SisoColorTokens.Gray90
            )
            Spacer(Modifier.size(24.dp))
            Box(
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .background(SisoColorTokens.Gray20, RoundedCornerShape(999.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // 바텀 올리기
                        bottomState = true

                    },
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .padding(top = 15.5.dp, bottom = 15.5.dp, start = 16.dp)
                            .fillMaxWidth(locationTextFloat),
                        text = resultText.ifBlank { "검색" },
                        style = SisoTypoTokens.Body2,
                        color = SisoColorTokens.Gray70,
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.size(if (resultText.isNotBlank())3.dp
                    else 16.dp))
                    if (resultText.isNotBlank()) {
                        Icon(
                            modifier = Modifier.size(24.dp)
                                .padding(end = 16.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_text_edit),
                            tint = SisoColorTokens.Gray40,
                            contentDescription = ""
                        )


                    }
                }
            }

            Spacer(Modifier.size(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    // 현재 위치로 설정하기 api
                }
            ){
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_crosshair),
                    tint = SisoColorTokens.Gray40,
                    contentDescription = ""
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    modifier = Modifier.height(23.dp),
                    text = "현재 위치로 설정하기",
                    style = SisoTypoTokens.Body2,
                    color = SisoColorTokens.Gray70,
                    textAlign = TextAlign.Start
                )
            }
        }

        if (resultText.isNotBlank()) {
            Column(
                Modifier.align(Alignment.BottomCenter)
            ) {
                CommonActiveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    text = "완료하기",
                ){
                    viewModel.locationComplete(input = resultText)
                    {input->
                        // 완료후 화면을 내리는 네비
                        popBackStack(input)
                    }
                }
                Spacer(Modifier.size(73.dp))
            }

        }
        if (bottomState)
            ModalBottomSheet(
                modifier = Modifier.systemBarsPadding(),
                onDismissRequest = {
                    // 바텀 내리기
                    bottomState = false
                    // 하위 지역 모드 초기화
                    viewModel.setBottomLocation("")
                    // 검색 버튼 텍스트 초기화
                    resultText = ""
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
                        text = "지역선택",
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
                            // 하위 지역 모드 초기화
                            viewModel.setBottomLocation("")
                            // 검색 버튼 텍스트 초기화
                            resultText = ""
                        }
                    ){
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
                if (bottomLocation.name.isEmpty()) {
                    topLocations.name.forEach { locationName->
                        LocationText(input = locationName) {
                            //바텀 내용 하위 지역 모드 설정
                            viewModel.setBottomLocation(locationName)
                            resultText = locationName
                        }
                    }
                }else{
                    bottomLocation.name.forEach {
                        LocationText(input = it) {
                            // 하위 지역 추가
                            resultText += " $it"
                            // 하위 지역 모드 초기화
                            viewModel.setBottomLocation("")
                            // 바텀 내리기
                            bottomState = false
                        }
                    }
                }

            }
    }

}

@Composable
fun LocationText(
    input: String,
    option: () -> Unit
){
    Column (
        modifier = Modifier
            .clickable{
                option()
            }
    ){
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = input,
            style = SisoTypoTokens.Body2,
            color = SisoColorTokens.Black,
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview
@Composable
fun LocationEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it

            val inputStream = LocalContext.current.resources.openRawResource(R.raw.korea_regions_ordered)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val fakeLocationRepositoryImpl = FakeLocationRepositoryImpl(jsonString)
            val topUseCase = TopLocationUseCase(fakeLocationRepositoryImpl)
            val bottomUseCase = BottomLocationUseCase(fakeLocationRepositoryImpl)
            LocationEditInfoScreen(FakeLocationEditInfoScreenViewModel(
                topUseCase,
                bottomUseCase
            ))
        }
    }
}