package com.likelion.login.agree_to_terms_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.login.first_loginInfo_screen.FontText
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens
import kotlin.collections.forEachIndexed

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun AgreeToTermsScreen(
    viewModel: AgreeToTermsScreenViewModelType,
    onNavigateNext: () -> Unit
) {
    val sideDp = 16.dp
    val scrollState = rememberScrollState()
    val introductionFontStyle = SisoTypoTokens.Body1
    val agreeList = listOf(
        1L to "(필수) 이용약관 동의",
        2L to "(선택) 마케팅 정보 수신",
    )
    val agreeContinueBoolean by viewModel.agreeContinueBoolean.collectAsStateWithLifecycle()

    Column {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left
        ) {
            Spacer(Modifier.padding(50.dp))
            Column(
                modifier = Modifier
                    .padding(start = sideDp, end = sideDp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                FontText(
                    text = "시팅에 어서오세요",
                    style = introductionFontStyle,
                    textColor = SisoColorTokens.GrayScale90,
                    padding = 0.dp,
                )
                FontText(
                    text = "새로운 인연을 만나기전에",
                    style = introductionFontStyle,
                    textColor = SisoColorTokens.GrayScale90,
                    padding = 0.dp,
                )
                FontText(
                    text = "동의가 필요해요",
                    style = introductionFontStyle,
                    textColor = SisoColorTokens.GrayScale90,
                    padding = 0.dp,
                )
            }


            Spacer(Modifier.padding(35.dp))
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            ) {
                AgreeRepeatRadioButton(
                    termsList = agreeList,
                    onClick = { continueBoolean ->
                        viewModel.agreeContinueBooleanUpdate(continueBoolean)
                        Log.d("radioRemember", "[agreeContinueBoolean]")
                        Log.d("radioRemember", viewModel.agreeContinueBoolean.toString())
                    },
                )
            }




            Spacer(Modifier.padding(150.dp))

        }
        Button(
            modifier = Modifier
                .padding(start = sideDp, end = sideDp)
                .fillMaxWidth()
                .height(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Orange30,
                disabledContainerColor = SisoColorTokens.GrayScale50
            ),
            onClick = {
                onNavigateNext()
            },
            enabled = agreeContinueBoolean
        ) {
            Text(
                text = "모두 동의",
                style = SisoTypoTokens.Button1,
                color = if (agreeContinueBoolean == true) SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale60,
                fontSize = 22.sp
            )
        }
        Spacer(Modifier.padding(12.dp))
    }


}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AgreeRepeatRadioButton(
    termsList: List<Pair<Long, String>>,
    onClick: (Boolean) -> Unit = {}
) {
    val checkIdList = remember {
        mutableStateListOf<Long>()
    }

    termsList.forEachIndexed { idx, (id, text) ->
        Row(
            modifier = Modifier
                .clickable {
                    if (checkIdList.contains(id)) {
                        checkIdList.remove(id)
                    } else {
                        checkIdList.add(id)
                    }

                    onClick(checkIdList.size == termsList.size)
                    Log.d("radioRemember", "[onClick]")
                    Log.d("radioRemember", "[checkIdList]")
                    Log.d("radioRemember", "${checkIdList.size == termsList.size}")
                    Log.d("radioRemember", checkIdList.toString())
                }
                .padding(top = 22.dp, bottom = 22.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FontText(
                fillMaxFloat = 0.9F,
                text = text, style = SisoTypoTokens.Body4, padding = 0.dp,
                textColor = SisoColorTokens.GrayScale90,
            )

            AsyncImage(
                modifier = Modifier.clickable(
                    onClick = {
                        if (checkIdList.contains(id)) {
                            checkIdList.remove(id)
                        } else {
                            checkIdList.add(id)
                        }

                        onClick(checkIdList.size == termsList.size)
                        Log.d("radioRemember", "[onClick]")
                        Log.d("radioRemember", "[checkIdList]")
                        Log.d("radioRemember", checkIdList.toString())
                    }
                ),
                model = if (checkIdList.contains(id)) R.drawable.select
                else R.drawable.unselect,
                contentDescription = ""
            )

        }
    }
    Log.d("radioRemember", "[radioRemember]")
    Log.d("radioRemember", checkIdList.toString())
}

@Composable
@Preview
fun AgreeToTermsScreenPreview() {
    Surface(
        color = SisoColorTokens.White
    ) {
        AgreeToTermsScreen(FakeAgreeToTermsScreenViewModel(), {})
    }

}