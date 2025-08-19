package com.lion.mypage.first_edit_info_screen

import android.R.id.input
import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.likelion.ui.R
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoFontSizeTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstEditInfoScreen(
    viewModel: FirstEditInfoScreenViewModelType
){
    val sideDp = 16.dp
    var nameText by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    var introduceText by rememberSaveable { mutableStateOf("") }
    val introduceTextEnd = 50
    val textPlaceholderColor = SisoColorTokens.GrayScale50
    val fistContinueBoolean by  viewModel.fistContinueBoolean.collectAsStateWithLifecycle()
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
            color = SisoColorTokens.GrayScale90
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
        Text(
            text = "닉네임",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = SisoColorTokens.White)
                .clip(RoundedCornerShape(25.dp)),
            value = nameText,
            onValueChange = { input ->
                nameText = input
                viewModel.nameUpdate(nameText)
                viewModel.fistContinueBooleanUpdate(
                    nameNotBlank = nameText.isNotBlank(), ageNotBlank = ageText.isNotBlank()
                )
            },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(

                    value = nameText,
                    placeholder = {
                        Text(
                            modifier = Modifier.height(23.dp),
                            text = "이것은 닉네임입니다.",
                            fontSize = SisoFontSizeTokens.Label1,
                            color = textPlaceholderColor
                        )
                    },
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    container = {

                        Box(modifier = Modifier.drawBehind {
                            drawRect(SisoColorTokens.GrayScale20)
                        })

                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = rememberAsyncImagePainter(R.drawable.text_edit),
                            tint = SisoColorTokens.GrayScale40,
                            contentDescription = ""
                        )
                    }
                )

            },


            )
        Spacer(modifier = Modifier.size(size = 28.dp))
        Text(
            text = "나이",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.dp, color = SisoColorTokens.White)
                .clip(RoundedCornerShape(25.dp)),
            value = ageText,
            onValueChange = { input ->
                ageText = input
                viewModel.ageUpdate(ageText)
                viewModel.fistContinueBooleanUpdate(
                    nameNotBlank = nameText.isNotBlank(), ageNotBlank = ageText.isNotBlank()
                )
            },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(

                    value = ageText,
                    placeholder = {
                        Text(
                            modifier = Modifier.height(23.dp),
                            text = "나이를 입력해주세요",
                            fontSize = SisoFontSizeTokens.Label1,
                            color = textPlaceholderColor
                        )
                    },
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    container = {
                        Box(modifier = Modifier.drawBehind {
                            drawRect(SisoColorTokens.GrayScale20)
                        })
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = rememberAsyncImagePainter(R.drawable.text_edit),
                            tint = SisoColorTokens.GrayScale40,
                            contentDescription = ""
                        )
                    }
                )

            }
        )
        Spacer(modifier = Modifier.size(size = 28.dp))

        Text(
            text = "자기소개",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 8.dp))
        // 여기에 EditText(텍스트 필드) 추가
        CommonOutlinedTextFiled(
            modifier = Modifier
                .fillMaxWidth()
                .height(206.dp),
            placeholderText = "안녕하세요. 인생의 황혼기에 접어들었지만, 늘 새로운 경험과 사랑을 찾아 나아가고 있습니다. 서로를 이해하며 함께할 수 있는 분을 기다립니다.",
            value = introduceText, // collect된 실시간 변경된 스트링 값을 넣는다.
            onValueChange = { input -> // 새롭게 변경된 문자를 넘겨줌
                introduceText = if(input.length>=introduceTextEnd)
                    input.substring(0,introduceTextEnd)
                else input
                viewModel.introduceUpdate(input = introduceText)
            }
        )
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${introduceText.length}/$introduceTextEnd",
            fontSize = SisoFontSizeTokens.Label1,
            textAlign = TextAlign.End,
            color = textPlaceholderColor
        )
        Spacer(modifier = Modifier.size(size = 75.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Orange30,
                disabledContainerColor = SisoColorTokens.GrayScale30
            ),
            onClick = {
                viewModel.fistContinueBooleanUpdate(
                    nameNotBlank = nameText.isNotBlank(), ageNotBlank = ageText.isNotBlank()
                )
            },
            enabled = fistContinueBoolean
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,
                color = if (fistContinueBoolean == true) SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale50,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.size(size = 58.dp))
    }
}

@Preview
@Composable
fun FirstEditInfoScreenPreview(){

    SisoTheme{
        Scaffold {
            it
            FirstEditInfoScreen(FakeFirstEditInfoScreenViewModel())
        }
    }
}
