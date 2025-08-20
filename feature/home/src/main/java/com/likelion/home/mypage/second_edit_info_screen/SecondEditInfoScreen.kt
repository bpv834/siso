package com.likelion.home.mypage.second_edit_info_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoFontSizeTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondEditInfoScreen(
    viewModel: SecondEditInfoScreenViewModelType
) {
    val sideDp = 16.dp
    var heightText by rememberSaveable { mutableStateOf("") }
    var weightText by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = sideDp, end = sideDp)
            .fillMaxHeight()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = AbsoluteAlignment.Left
    ) {
        Spacer(modifier = Modifier.size(size = 16.dp))
        Text(
            text = "기본정보를 제공해주세요",
            style = SisoTypoTokens.Title1,
            color = SisoColorTokens.Gray90
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(
            text = "키",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.Gray50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = SisoColorTokens.White)
                .clip(RoundedCornerShape(25.dp)),
            value = heightText,
            onValueChange = { input ->
                heightText = input.replace(Regex("[^0-9]"), "")
                //viewModel.heightUpdate(heightText)
            },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(

                    value = heightText,
                    placeholder = {
                        Text(
                            modifier = Modifier.height(23.dp),
                            text = "이것은 닉네임입니다.",
                            fontSize = SisoFontSizeTokens.Label1
                        )
                    },
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    container = {

                        Box(modifier = Modifier.drawBehind {
                            drawRect(SisoColorTokens.Gray20)
                        })

                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = rememberAsyncImagePainter(R.drawable.text_edit),
                            tint = SisoColorTokens.Gray40,
                            contentDescription = ""
                        )
                    }
                )

            },


            )
    }
}

@Preview
@Composable
fun SecondEditInfoScreenPreview(){
    SecondEditInfoScreen(FakeSecondEditInfoScreenViewModel())
}