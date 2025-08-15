package com.likelion.login.agree_to_terms_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens
import kotlin.collections.forEachIndexed

@Composable
fun AgreeToTermsScreen(
    viewModel: AgreeToTermsScreenViewModelType
) {
    val sideDp = 16.dp
    val scrollState = rememberScrollState()
    val introductionFontStyle = SisoTypoTokens.Body1
    val agreeList = listOf(
            1L to "(필수) 이용약관 동의",
            2L to "(선택) 마케팅 정보 수신",
    )
    val agreeContinueBoolean = viewModel.agreeContinueBoolean.collectAsStateWithLifecycle()


        Column(
            modifier = Modifier
                .padding(start = sideDp, end = sideDp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left
        ) {
            Spacer(modifier = Modifier.size(size = 60.dp))
            Text(
                text = "시팅에 어서오세요" +
                        "\n새로운 인연을 만나기전에"+
                        "\n동의가 필요해요",
                style = introductionFontStyle,
                color = SisoColorTokens.GrayScale90,
            )
            Spacer(modifier = Modifier.size(size = 68.dp))

                AgreeRepeatRadioButton(
                    termsList = agreeList,
                    onClick = {continueBoolean->
                        viewModel.agreeContinueBooleanUpdate(continueBoolean)
                        Log.d("radioRemember", "[agreeContinueBoolean]")
                        Log.d("radioRemember", viewModel.agreeContinueBoolean.toString())
                    },
                )

            Spacer(modifier = Modifier.size(size = 286.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SisoColorTokens.Gold40,
                    disabledContainerColor = SisoColorTokens.GrayScale30
                ),
                onClick = {

                },
                enabled = agreeContinueBoolean.value
            ) {
                Text(
                    text = "계속하기",
                    style = SisoTypoTokens.Button1,
                    color = if(agreeContinueBoolean.value == true)SisoColorTokens.GrayScale90
                    else SisoColorTokens.GrayScale50,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.size(size = 56.dp))

    }


}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AgreeRepeatRadioButton(
    termsList: List<Pair<Long, String>>,
    onClick:(Boolean)->Unit = {}
){
    val checkIdList  = remember {
        mutableStateListOf<Long>()
    }

    termsList.forEachIndexed { idx, (id, text) ->
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(27.dp)
                .clickable {
                    if (checkIdList.contains(id)) {
                        checkIdList.remove(id)
                    } else {
                        checkIdList.add(id)
                    }

                    onClick(checkIdList.size == termsList.size)
                },
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = text,
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale90,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                    modifier = Modifier.size(24.dp)
                        .align(Alignment.CenterEnd)
                        .clickable(
                        onClick = {
                            if (checkIdList.contains(id)) {
                                checkIdList.remove(id)
                            } else {
                                checkIdList.add(id)
                            }

                            onClick(checkIdList.size == termsList.size)

                        }
                    ),
                    model =  if (checkIdList.contains(id))R.drawable.select
                    else R.drawable.unselect,
                    contentDescription = ""
                )

        }
        if (idx < termsList.size - 1)Spacer(Modifier.size(22.dp))

    }
    Log.d("radioRemember", "[radioRemember]")
    Log.d("radioRemember", checkIdList.toString())
}

@Composable
@Preview
fun AgreeToTermsScreenPreview(){
    Surface(
        color = SisoColorTokens.White
    ){
        AgreeToTermsScreen(FakeAgreeToTermsScreenViewModel())
    }

}