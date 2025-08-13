package com.likelion.login.agree_to_terms_screen

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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.login.first_loginInfo_screen.FontText
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

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
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ) {
                FontText(
                    fillMaxFloat = 0.9F,
                    text = "(필수) 이용약관 동의", style = SisoTypoTokens.Body4,
                    textColor = SisoColorTokens.GrayScale90,
                    padding = 0.dp, onClick = {
                        viewModel.requiredTermsAgreeBooleanUpdate(
                            !viewModel.requiredTermsAgreeBoolean
                        )
                    }
                )
                AsyncImage(
                    modifier = Modifier.clickable(
                        onClick = {

                        }
                    ),
                    model = R.drawable.select,
                    contentDescription = ""
                )
//                AsyncImage(
//                    modifier = Modifier.clickable(
//                        onClick = {
//                            viewModel.requiredTermsAgreeBooleanUpdate(
//                                !viewModel.requiredTermsAgreeBoolean
//                            )
//                        }
//                    ),
//                    model = if (viewModel.requiredTermsAgreeBoolean) R.drawable.select
//                    else R.drawable.unselect,
//                    contentDescription = ""
//                )

            }
            Spacer(Modifier.padding(35.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FontText(
                    fillMaxFloat = 0.9F,
                    text = "(선택) 마케팅 정보 수신", style = SisoTypoTokens.Body4,
                    textColor = SisoColorTokens.GrayScale90,
                    padding = 0.dp, onClick = {
                        viewModel.receptionAgreeBooleanUpdate(
                            !viewModel.receptionAgreeBoolean
                        )
                    }
                )
                AsyncImage(
                    modifier = Modifier.clickable(
                        onClick = {

                        }
                    ),
                    model = R.drawable.select,
                    contentDescription = ""
                )
/*                AsyncImage(
                    modifier = Modifier.clickable(
                        onClick = {
                            viewModel.receptionAgreeBooleanUpdate(
                                !viewModel.receptionAgreeBoolean
                            )
                        }
                    ),
                    model = if (viewModel.receptionAgreeBoolean) R.drawable.select
                    else R.drawable.unselect,
                    contentDescription = ""
                )*/

            }


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
                viewModel.agreeContinueBooleanUpdate()
            },
            enabled = viewModel.agreeContinueBoolean
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,
                color = if(viewModel.agreeContinueBoolean == true)SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale50,
                fontSize = 22.sp
            )
        }
        Spacer(Modifier.padding(12.dp))
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