package com.likelion.login.FirstLoginInfoScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.likelion.login.FirstLoginInfoScreen.FirstLoginInfoScreenViewModel
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoFontSizeTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstLoginInfoScreen(
    viewModel: FirstLoginInfoScreenViewModel = FirstLoginInfoScreenViewModel()
){
    val sideDp = 16.dp

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = sideDp, end = sideDp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = AbsoluteAlignment.Left
    ) {
        Spacer(modifier = Modifier.size(size = 8.dp))
        AsyncImage(
            model = R.drawable.img_circle_bar_login1,
            contentDescription = ""
        )

        FontText(text = "기본정보를 제공해주세요", style = SisoTypoTokens.Title1,
            padding = 12.dp,)
        FontText(text = "닉네임", style = SisoTypoTokens.Label1,
            padding = 12.dp,
            textColor = SisoColorTokens.GrayScale50
        )
        Spacer(Modifier.padding(12.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = SisoColorTokens.White)
                .clip(RoundedCornerShape(25.dp)),
            value = viewModel.nameState,
            onValueChange = {input->
                viewModel.nameUpdate(input)
            },
            decorationBox = @Composable{innerTextField->
                TextFieldDefaults.DecorationBox(

                    value = viewModel.nameState,
                    placeholder = {
                        Text(
                            text= "이것은 닉네임입니다.",
                            fontSize = SisoFontSizeTokens.Body2
                        )
                    },
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    container = {
                        Box(modifier = Modifier.drawBehind{
                            drawRect(SisoColorTokens.GrayScale20)
                        })
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {

                            }) {
                            Icon(
                                modifier = Modifier.size(46.dp),
                                imageVector = Icons.Default.Edit,
                                contentDescription = ""
                            )
                        }
                    }
                )

            }
        )
        FontText(text = "나이", style = SisoTypoTokens.Label1,
            padding = 12.dp,
            textColor = SisoColorTokens.GrayScale50
        )
        Spacer(Modifier.padding(12.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = SisoColorTokens.White)
                .clip(RoundedCornerShape(25.dp)),
            value = viewModel.nameState,
            onValueChange = {input->
                viewModel.nameUpdate(input)
            },
            decorationBox = @Composable{innerTextField->
                TextFieldDefaults.DecorationBox(

                    value = viewModel.nameState,
                    placeholder = {
                        Text(
                            text= "나이를 입력해주세요",
                            fontSize = SisoFontSizeTokens.Body2
                        )
                    },
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    container = {
                        Box(modifier = Modifier.drawBehind{
                            drawRect(SisoColorTokens.GrayScale20)
                        })
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {

                            }) {
                            Icon(
                                modifier = Modifier.size(46.dp),
                                imageVector = Icons.Default.Edit,
                                contentDescription = ""
                            )
                        }
                    }
                )

            }
        )
        FontText(text = "내 성별", SisoTypoTokens.Label1,
            textColor = SisoColorTokens.GrayScale50,padding = 12.dp)

        Row {
            RepeatRadioButton(viewModel.myRadioButtons)
        }
        FontText(text = "매칭 성별", SisoTypoTokens.Label1,
            textColor = SisoColorTokens.GrayScale50,padding = 12.dp)
        FontText(text = "동성선택시 동성친구 이성선택시 이성친구를\n추천해 드려요.", SisoTypoTokens.Label1,
            textColor = SisoColorTokens.GrayScale50,padding = 0.dp)

        Row {
            RepeatRadioButton(viewModel.pairRadioButtons)
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Orange30,
                disabledContainerColor = SisoColorTokens.GrayScale50
            ),
            onClick = {
                viewModel.fistContinueBooleanUpdate(true)
            },
            enabled = viewModel.fistContinueBoolean
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,
                color = if(viewModel.fistContinueBoolean == true)SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale50,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
fun FontText(
    text: String,
    style: TextStyle,
    textColor: Color = Color.Unspecified,
    padding: Dp = 16.dp,
    onClick:(()->Unit)? = null,
    fillMaxFloat: Float = 1F,
){
    Column(
        modifier = if (onClick == null) Modifier.fillMaxWidth(fillMaxFloat)
        else Modifier.fillMaxWidth(fillMaxFloat).clickable(onClick = {
                    onClick()
                }
            )
    ) {
        Spacer(Modifier.padding(padding))
        Text(
            text = text,
            style = style,
            color = textColor
        )
    }

}

@Composable
fun RepeatRadioButton(radios: MutableList<Pair<String, Boolean>>){
    radios.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    radios.replaceAll {
                        it.copy(
                            second = (it.first == info.first)
                        )
                    }
                }
                .padding(end = 16.dp)
        ) {
            Text(
                text = info.first,
                style = SisoTypoTokens.Body1,
                fontSize = 21.sp
            )
            RadioButton(
                selected = info.second,
                colors = RadioButtonDefaults.colors(
                    selectedColor = SisoColorTokens.GrayScale90,
                    unselectedColor = SisoColorTokens.GrayScale30
                ),
                onClick = {
                    radios.replaceAll {
                        it.copy(
                            second = (it.first == info.first)
                        )
                    }
                }
            )

        }
    }
}

@Composable
@Preview
fun FirstLoginInfoPreview(){
    Surface(color = SisoColorTokens.White) {
        FirstLoginInfoScreen()
    }

}