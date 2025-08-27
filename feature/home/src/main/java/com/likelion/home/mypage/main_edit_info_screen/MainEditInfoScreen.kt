package com.likelion.home.mypage.main_edit_info_screen

import android.R.attr.action
import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.ObserverHandle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.likelion.home.navigation.Pub
import com.likelion.home.navigation.getString
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.component.chip.CommonChip
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import kotlin.collections.listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainEditInfoScreen(
    viewModel: MainEditInfoScreenViewModelType,
    saveHandle: SavedStateHandle,
    action:List<()->Unit> = listOf()
) {
    val potoNavigation = {if (action.isNotEmpty()) action[0]()}
    val voiceNavigation = { if (action.isNotEmpty()) action[1]() }
    val locationBlank =
        saveHandle.getString("location") == null
    val locationNavigation = {if (action.isNotEmpty()) action[2]()}
    val religionBlank =
        saveHandle.getString("religion") == null
    val religionNavigation = {if (action.isNotEmpty()) action[3]()}
    val smokingNavigation = {if (action.isNotEmpty()) action[4]()}
    val alcoholNavigation = { if (action.isNotEmpty()) action[5]() }
    val mbtiNavigation = {if (action.isNotEmpty()) action[6]()}
    val interestNavigation = {if (action.isNotEmpty()) action[7]() }
    val matchingNavigation = {if (action.isNotEmpty()) action[8]()}

    // LocalConfiguration을 사용하여 현재 구성 정보를 가져옵니다.
    val configuration = LocalConfiguration.current

    // 화면의 너비와 높이를 Dp 단위로 가져옵니다.
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val playButtonSize = 24.dp
    val sliderVectorPadding = 7.75.dp
    val playtimeSize = DpSize(48.5.dp,23.dp)
    // 여러 같은 패딩 x 5 + slider 사이드 x 2 + 아이콘 + 재생시간 크기 를 모두 뺸 사이즈
    val sliderSizing = screenWidthDp - ((16*5).dp + sliderVectorPadding *2 + playButtonSize *2 + playtimeSize.width)

    var playState by remember { mutableStateOf(false) }

    val myRadioButtons by viewModel.myRadioButtons.collectAsState()
    val pairRadioButtons by viewModel.pairRadioButtons.collectAsState()
    val fistContinueBoolean by viewModel.fistContinueBoolean.collectAsState()
    val nameState by viewModel.nameState.collectAsState()
    val nameStateRange = TextRange(0,10)
    var introduceText by remember { mutableStateOf("") }
    val introduceTextRange = TextRange(0,50)
    var ageText by remember { mutableStateOf("") }
    val ageTextRange = TextRange(0,3)
    val sliderVectorList = listOf(
        12, 18, 12, 8, 12, 12, 18, 12, 6
    )

    val appbarTitle = stringResource(com.likelion.home.R.string.main_edit)


    val interestList = listOf(
        "나의 관심사" to { interestNavigation() },
        "매칭 상대와의 관계" to { matchingNavigation() }
    )

    val interestChipList = listOf(
        "나의 관심사를 골라주세요" to listOf<String>(),
        "어떤 관계를 원하시나요?" to listOf()
    )
    Box(
        modifier = Modifier.fillMaxHeight()
    ){
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            potoNavigation()
                        }
                ) {
                    AsyncImage(
                        modifier = Modifier.size(120.dp, 120.dp),
                        model = R.drawable.example_profile,
                        contentDescription = ""
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset((2).dp, (3).dp)
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SisoColorTokens.Gray5),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(SisoColorTokens.Gray20),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                tint = SisoColorTokens.Gray60,
                                painter = rememberAsyncImagePainter(R.drawable.ic_text_edit),
                                contentDescription = ""
                            )
                        }

                    }
                }
            }

            Spacer(Modifier.size(12.dp))
            Text(
                modifier = Modifier.height(24.dp),
                text = "닉네임",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(8.dp))
            // 여기에 EditText(텍스트 필드) 추가
            CommonOutlinedTextFiled(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                placeholderText = "닉네임을 입력해주세요",
                value = nameState, // collect된 실시간 변경된 스트링 값을 넣는다.
                onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                    viewModel.nameUpdate(
                        if (newText.length > nameStateRange.length)
                            newText.substring(nameStateRange)
                        else newText
                    )
                }
            )
            Spacer(Modifier.size(24.dp))
            Text(
                modifier = Modifier.height(24.dp),
                text = "나이",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 여기에 EditText(텍스트 필드) 추가
                CommonOutlinedTextFiled(
                    modifier = Modifier
                        .size(86.dp, 52.dp)
                        .padding(end = 6.dp),
                    placeholderText = "",
                    value = ageText, // collect된 실시간 변경된 스트링 값을 넣는다.
                    onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                        val temp = if (newText.length > ageTextRange.length)
                            newText.substring(ageTextRange)
                        else newText
                        ageText = temp.replace(Regex("[^0-9]"), "")
                    }
                )
                Text(
                    modifier = Modifier.height(24.dp),
                    text = "세",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Gray50,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.size(24.dp))
            Text(
                modifier = Modifier.height(24.dp),
                text = "자기소개",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray50,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 음성 재생 바
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth(0.889F)
                        .clip(RoundedCornerShape(999.dp))
                        .background(SisoColorTokens.Gray60),
                ) {
                    //음성 재생 아이콘 구현
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //이미지 로드
                        IconButton(
                            modifier = Modifier.size(playButtonSize),
                            onClick = {
                                playState = !playState
                                // 재생 / 정지
                                if (!playState) {
                                    // 재생하기

                                } else {
                                    // 중지하기
                                }
                            }
                        ) {
                            Icon(
                                tint = SisoColorTokens.Gray10,
                                imageVector = ImageVector.vectorResource(
                                    if (!playState) R.drawable.ic_play
                                    else R.drawable.ic_pause
                                ),
                                contentDescription = ""
                            )
                        }

                        // 박스 크기만큼 뺀 패딩 9 - 1.25
                        Spacer(Modifier.size(sliderVectorPadding))
                        // 슬라이더 대체 이미지
                        var temp = sliderSizing
                        while (temp > 2.5.dp) {
                            sliderVectorList.forEachIndexed { idx, height ->
                                temp -= 2.5.dp
                                if (temp > 2.5.dp)
                                    Box(
                                        Modifier
                                            .size(width = 2.5.dp, height = height.dp)
                                            .clip(RoundedCornerShape(999.dp))
                                            .drawWithContent {
                                                drawRect(SisoColorTokens.White)
                                            })
                                else
                                    return@forEachIndexed
                                temp -= 3.5.dp
                                if (temp > 3.5.dp)
                                    Spacer(Modifier.size(3.5.dp))
                                else
                                    return@forEachIndexed

                            }
                        }
                        // 박스 크기만큼 뺀 패딩 9 - 1.25
                        Spacer(Modifier.size(sliderVectorPadding))
                        Text(
                            modifier = Modifier.size(playtimeSize),
                            text = "00:15",
                            style = SisoTypoTokens.Label1,
                            color = SisoColorTokens.Gray10,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.size(16.dp))

                    }
                }
                Spacer(Modifier.size(16.dp))
                Icon(
                    modifier = Modifier.size(24.dp).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        voiceNavigation()
                    },
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_text_edit
                    ),
                    tint = SisoColorTokens.Gray60,
                    contentDescription = ""
                )
            }

            Spacer(Modifier.size(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(206.dp),
            ) {

                // 여기에 EditText(텍스트 필드) 추가
                CommonOutlinedTextFiled(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(206.dp),
                    placeholderText = "안녕하세요. 인생의 황혼기에 접어들었지만, 늘 새로운 경험과 사랑을 찾아 나아가고 있습니다. 서로를 이해하며 함께할 수 있는 분을 기다립니다.",
                    value = introduceText, // collect된 실시간 변경된 스트링 값을 넣는다.
                    onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                        introduceText = if (newText.length > introduceTextRange.length)
                            newText.substring(introduceTextRange)
                        else newText
                    }
                )

                Text(
                    modifier = Modifier
                        .height(22.dp)
                        .align(Alignment.BottomEnd)
                        .offset((-16).dp, (-16).dp),
                    text = "${introduceText.length}/${introduceTextRange.end}",
                    style = SisoTypoTokens.Label1,
                    color = SisoColorTokens.Gray50
                )

            }

            Spacer(Modifier.size(48.dp))

            TitleText("기본정보")

            Spacer(Modifier.size(32.dp))
            Column(
                modifier = Modifier.height(59.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.height(23.dp),
                    text = "내 성별",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Gray50,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(12.dp))
                Row(
                    modifier = Modifier.height(24.dp)
                ) {
                    EditInfoRepeatRadioButton(myRadioButtons) {
                        viewModel.fistContinueBooleanUpdate()
                    }
                }
            }
            Spacer(Modifier.size(24.dp))

            Column(
                modifier = Modifier.height(59.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.height(23.dp),
                    text = "매칭 성별",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Gray50,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(12.dp))
                Row(
                    modifier = Modifier.height(24.dp)
                ) {
                    EditInfoRepeatRadioButton(pairRadioButtons) {
                        viewModel.fistContinueBooleanUpdate()
                    }
                }
            }
            Spacer(Modifier.size(32.dp))
            InfoEditScreenButton(
                titleText = "지역",
                selectText = if(locationBlank) "나의 지역을 등록해주세요"
                else saveHandle.get<String>("location")!!,
                color = if(locationBlank) SisoColorTokens.Gray50
                else SisoColorTokens.Gray90,
            ) {
                locationNavigation()
            }

            Spacer(Modifier.size(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(27.dp),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .height(23.dp),
                    text = "기본정보",
                    style = SisoTypoTokens.SubTitle1,
                    color = SisoColorTokens.Gray90,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(68.dp, 27.dp)
                        .background(SisoColorTokens.Gold40, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.size(52.dp, 23.dp),
                        text = "+30%",
                        style = SisoTypoTokens.SubTitle1,
                        color = SisoColorTokens.Gray90,
                        textAlign = TextAlign.Center
                    )
                }

            }
            Spacer(Modifier.size(36.dp))
            InfoEditScreenButton(
                titleText = "종교",
                selectText = if (religionBlank)"정보를 입력해주세요"
                else saveHandle.getString("religion")!!,
                color = if(religionBlank) SisoColorTokens.Gray50
                else SisoColorTokens.Gray90,
            ) {
                religionNavigation()
            }
            Spacer(Modifier.size(24.dp))
            InfoEditScreenButton(
                titleText = "흡연",
                selectText = "정보를 입력해주세요"
            ) {
                smokingNavigation()
            }
            Spacer(Modifier.size(24.dp))
            InfoEditScreenButton(
                titleText = "음주",
                selectText = "정보를 입력해주세요"
            ) {
                alcoholNavigation()
            }
            Spacer(Modifier.size(24.dp))
            InfoEditScreenButton(
                titleText = "MBTI",
                selectText = "정보를 입력해주세요"
            ) {
                mbtiNavigation()
            }
            Spacer(Modifier.size(48.dp))

            TitleText("관심사 / 취향 태그")

            interestList.forEachIndexed { index, (sub, onclick) ->
                Spacer(Modifier.size(32.dp))
                Row(
                    modifier = Modifier.height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .height(23.dp)
                            .fillMaxWidth(0.889F),
                        text = sub,
                        style = SisoTypoTokens.SubTitle1,
                        color = SisoColorTokens.Gray50,
                        textAlign = TextAlign.Start
                    )
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            onclick()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_caret_right),
                            contentDescription = ""
                        )
                    }
                }
                Spacer(Modifier.size(12.dp))
                d("interestChipList", "$index ${interestChipList[index].first}")
                InterestRepeatChip(
                    emptyText = interestChipList[index].first,
                    list = interestChipList[index].second,
                )
            }
            // 59 + 62
            Spacer(Modifier.size(121.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(Modifier.size(8.dp))
            CommonActiveButton(
                modifier = null,
                text = "수정완료"
            ) {

            }
            Spacer(Modifier.size(72.dp))
        }
    }

}

@Composable
fun TitleText(
    titleText: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(27.dp),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .height(23.dp),
            text = titleText,
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.Gray90,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(68.dp, 27.dp)
                .background(SisoColorTokens.Gold40, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ){
            Text(
                modifier = Modifier.size(52.dp,23.dp),
                text = "+30%",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.Gray90,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun EditInfoRepeatRadioButton(
    radios: MutableList<Pair<String, Boolean>>,
    click: () -> Unit = {}
) {
    radios.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    radios.replaceAll {
                        it.copy(
                            second = (it.first == info.first)
                        )
                    }
                    click()
                }
        ) {
            Text(
                text = info.first,
                style = SisoTypoTokens.Body2,
            )
            Spacer(modifier = Modifier.size(size = 2.dp))
            RadioButton(
                selected = info.second,
                colors = RadioButtonDefaults.colors(
                    selectedColor = SisoColorTokens.Gray90,
                    unselectedColor = SisoColorTokens.Gray30
                ),
                onClick = {
                    radios.replaceAll {
                        it.copy(
                            second = (it.first == info.first)
                        )
                    }
                    click()
                }
            )

        }
        Spacer(modifier = Modifier.size(size = 24.dp))
    }
}

@Composable
fun InfoEditScreenButton(
    titleText: String,
    selectText: String,
    pubList: List<Pub> = emptyList(),
    color: Color = SisoColorTokens.Gray50,
    icon: ImageVector = ImageVector.vectorResource(com.likelion.home.R.drawable.chevron_down),
    click: () -> Unit = {}
) {
    Text(
        modifier = Modifier.height(23.dp),
        text = titleText,
        style = SisoTypoTokens.SubTitle1,
        color = SisoColorTokens.Gray50,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(12.dp))
    OutlinedButton(
        onClick = {
            click()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = SisoColorTokens.Gray20,
            contentColor = SisoColorTokens.Gray50
        ),
        border = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .fillMaxWidth(0.889F),
            ) {
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = selectText,
                    style = SisoTypoTokens.Body2,
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.size(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestRepeatChip(
    emptyText: String,
    list: List<String>
){
    if (list.isEmpty()||list.all { it.isBlank() })
        Box(
            modifier = Modifier
                .height(48.dp)
                .background(SisoColorTokens.Gray20, RoundedCornerShape(999.dp)),
        ){
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 18.dp, end = 18.dp),
                text = emptyText,
                style = SisoTypoTokens.Body2,
                color = SisoColorTokens.Gray70,
                textAlign = TextAlign.Center
            )
        }

    else
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(12.dp)
        ) {
            list.forEach {
                if (it.isNotBlank())
                CommonChip(
                    text = it,
                    isSelected = false
                ) { }
            }
        }
}


@Preview
@Composable
fun MainEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            MainEditInfoScreen(
                viewModel = FakeMainEditInfoScreenViewModel(),
                saveHandle = SavedStateHandle(),
            )
        }
    }
}