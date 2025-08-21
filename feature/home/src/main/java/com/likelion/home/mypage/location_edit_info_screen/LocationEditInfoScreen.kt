package com.likelion.home.mypage.location_edit_info_screen

import android.R.attr.end
import android.content.res.Resources
import android.util.Log.d
import androidx.annotation.ArrayRes
import com.likelion.ui.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationEditInfoScreen(
    viewModel: LocationEditInfoScreenViewModelType
) {

    val text by remember { mutableStateOf("") }
    val locationTextFloat = if (text.isBlank())  0.845F else 1F
    var bottomState by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val topLocation = stringArrayResource(R.array.상위위치)

    Box(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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
                    .height(54.dp).fillMaxWidth()
                    .background(SisoColorTokens.GrayScale20, RoundedCornerShape(999.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        if (text.isBlank()){
                            bottomState = true
                        }
                    },
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.padding(top = 15.5.dp, bottom = 15.5.dp, start = 16.dp)
                            .fillMaxWidth(locationTextFloat),
                        text = "검색",
                        style = SisoTypoTokens.Body2,
                        color = SisoColorTokens.GrayScale70,
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.size(if (text.isNotBlank())3.dp
                    else 16.dp))
                    if (text.isNotBlank()) {
                        IconButton(
                            modifier = Modifier.padding(end = 16.dp),
                            onClick = {
                                bottomState = true
                            }
                        ){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.text_edit),
                                tint = SisoColorTokens.GrayScale40,
                                contentDescription = ""
                            )
                        }

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
                    bottomState = true
                }
            ){
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.crosshair),
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
                    modifier = Modifier.fillMaxWidth().height(54.dp),
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
                onDismissRequest = {
                    bottomState = false
                },
                sheetState = sheetState,
                dragHandle = null
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(31.dp),
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
                        modifier = Modifier.padding(end = 20.dp)
                            .align(Alignment.CenterEnd),
                        onClick = {
                            bottomState = false
                        }
                    ){
                        Icon(
                            modifier = Modifier.size(24.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = ImageVector.vectorResource(R.drawable.text_edit),
                            tint = SisoColorTokens.GrayScale40,
                            contentDescription = ""
                        )
                    }
                }
                Spacer(Modifier.size(31.dp))



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
            .clip(RoundedCornerShape(3.dp))
            .clickable{
                option()
            }
    ){
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth()
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
            LocationEditInfoScreen(FakeLocationEditInfoScreenViewModel())
        }
    }
}