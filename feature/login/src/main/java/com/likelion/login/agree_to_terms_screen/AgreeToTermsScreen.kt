package com.likelion.login.agree_to_terms_screen

import android.R.attr.contentDescription
import android.R.attr.onClick
import android.annotation.SuppressLint
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.likelion.login.first_loginInfo_screen.FontText
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AgreeToTermsScreen(
    viewModel: AgreeToTermsScreenViewModel = AgreeToTermsScreenViewModel()
) {
    val sideDp = 16.dp
    val scrollState = rememberScrollState()
    val introductionFontStyle = SisoTypoTokens.Body1
    Column {
        Column(
            modifier = Modifier.padding(start = sideDp, end = sideDp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left
        ) {
            Spacer(Modifier.padding(50.dp))

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

            Spacer(Modifier.padding(35.dp))
            AgreeRepeatRadioButton(
                radio = viewModel.agreesBoolean,
                onClick = {index->
                    viewModel.agreeContinueBooleanUpdate(index = index)
                },
            )



            Spacer(Modifier.padding(150.dp))

        }
        Button(
            modifier = Modifier.padding(start = sideDp, end = sideDp)
                .fillMaxWidth()
                .height(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Orange30,
                disabledContainerColor = SisoColorTokens.GrayScale50
            ),
            onClick = {

            },
            enabled = viewModel.agreeContinueBoolean
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,
                color = if(viewModel.agreeContinueBoolean == true)SisoColorTokens.GrayScale90
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
    radio: MutableList<Pair<String, Boolean>>,
    onClick:(Int)->Unit = {}
){
    radio.forEachIndexed { index, info ->
        Row(
            modifier = Modifier.clickable{
                onClick(index)
            }.padding(top = 22.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FontText(
                fillMaxFloat = 0.9F,
                text = info.first, style = SisoTypoTokens.Body4, padding = 0.dp,
                textColor = SisoColorTokens.GrayScale90,
            )
            AsyncImage(
                    modifier = Modifier.clickable(
                        onClick = {
                            onClick(index)
                        }
                    ),
                    model = if (info.second) R.drawable.select
                    else R.drawable.unselect,
                    contentDescription = ""
                )
/*            RadioButton(
                selected = info.second,
                colors = RadioButtonDefaults.colors(
                    selectedColor = SisoColorTokens.GrayScale90,
                    unselectedColor = SisoColorTokens.GrayScale30
                ),
                onClick = {
                    radios[index] = info.copy(info.first,!info.second)
                    onClick()
                }

            )*/
        }
    }
}

@Composable
@Preview
fun AgreeToTermsScreenPreview(){
    Surface(
        color = SisoColorTokens.White
    ){
        AgreeToTermsScreen()
    }

}