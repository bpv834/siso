package com.likelion.home.mypage.location_edit_info_screen

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
import com.likelion.domain.mypage.repository.FakeLocationMapper
import com.likelion.domain.mypage.repository.FakeLocationRepositoryImpl
import com.likelion.domain.mypage.repository.LocationRepository
import com.likelion.domain.mypage.usecase.BottomLocationUseCase
import com.likelion.domain.mypage.usecase.TopLocationUseCase
//import com.likelion.data.mypage.repository.LocationRepositoryImpl
//import com.likelion.data.mypage.mapper.LocationMapper
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationEditInfoScreen(
    viewModel: LocationEditInfoScreenViewModelType,
    popBackStack: () -> Unit = {},
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    var tempText by remember { mutableStateOf("") }
    val locationTextFloat = if (text.isBlank())  0.845F else 1F
    var bottomState by remember { mutableStateOf(false) }
    var topLocation by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val topLocations = locationMapper.topLocationList

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
                color = SisoColorTokens.GrayScale90
            )
            Spacer(Modifier.size(24.dp))
            Box(
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .background(SisoColorTokens.GrayScale20, RoundedCornerShape(999.dp))
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
                        text = text.ifBlank { "검색" },
                        style = SisoTypoTokens.Body2,
                        color = SisoColorTokens.GrayScale70,
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.size(if (text.isNotBlank())3.dp
                    else 16.dp))
                    if (text.isNotBlank()) {
                        Icon(
                            modifier = Modifier.size(24.dp)
                                .padding(end = 16.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_text_edit),
                            tint = SisoColorTokens.GrayScale40,
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
                    tint = SisoColorTokens.GrayScale40,
                    contentDescription = ""
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    modifier = Modifier.height(23.dp),
                    text = "현재 위치로 설정하기",
                    style = SisoTypoTokens.Body2,
                    color = SisoColorTokens.GrayScale70,
                    textAlign = TextAlign.Start
                )
            }
        }

        if (text.isNotBlank()) {
            Column(
                Modifier.align(Alignment.BottomCenter)
            ) {
                CommonActiveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    text = "완료하기",
                ){
                    viewModel.locationComplete(input = text){
                        // 완료후 화면을 내리는 네비
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
                    topLocation = ""
                    // temp 초기화
                    tempText = ""
                    // 검색 버튼 텍스트 초기화
                    text = ""
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
                            topLocation = ""
                            // temp 초기화
                            tempText = ""
                            // 검색 버튼 텍스트 초기화
                            text = ""
                        }
                    ){
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_bottom_close),
                            tint = SisoColorTokens.GrayScale90,
                            contentDescription = ""
                        )
                    }
                }
                Spacer(Modifier.size(31.dp))
                if (topLocation.isBlank()) {
                    topLocations.forEach {
                        LocationText(input = it.first) {
                            //바텀 내용 하위 지역 모드 설정
                            topLocation = it.second
                            // 상위 지역 텍스트 임시 저장
                            tempText = it.first
                        }
                    }
                }else{
                    val resId = context.resources.getIdentifier(
                        "city_${ topLocation }",// 선택된 상위 위치
                        "array",
                        context.packageName
                    )
                    val locations = context.resources.getStringArray(resId)
                    locations.forEach {
                        LocationText(input = it) {
                            // 하위 지역 추가
                            text = "$tempText $it"
                            //바텀 내용 하위 지역 모드 초기화
                            topLocation = ""
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
            val fakeLocationRepositoryImpl = FakeLocationRepositoryImpl(FakeLocationMapper())
            val topUseCase = TopLocationUseCase(fakeLocationRepositoryImpl)
            val bottomUseCase = BottomLocationUseCase(fakeLocationRepositoryImpl)
            LocationEditInfoScreen(FakeLocationEditInfoScreenViewModel(
                topUseCase,
                bottomUseCase
            ))
        }
    }
}