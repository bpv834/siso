package com.likelion.login.first_loginInfo_screen

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.likelion.login.first_login_info_screen.FakeFirstLoginInfoScreenViewModel
import com.likelion.login.first_login_info_screen.FirstLoginInfoScreenViewModelType
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoFontSizeTokens
import com.likelion.ui.theme.SisoTypoTokens
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstLoginInfoScreen(
    viewModel: FirstLoginInfoScreenViewModelType,
    onNavigateNext: () -> Unit,
) {
    val sideDp = 16.dp
    var nameText by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    val fistContinueBoolean = viewModel.fistContinueBoolean.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = sideDp, end = sideDp)
            .fillMaxHeight()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = AbsoluteAlignment.Left
    ) {
        Spacer(modifier = Modifier.size(size = 24.dp))
        AsyncImage(
            model = R.drawable.img_circle_bar_login1,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(size = 24.dp))
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
                nameText = if (input.length >= 10) {
                    input.substring(0 until 10)
                } else {
                    input
                }
                viewModel.nameUpdate(nameText)
                viewModel.fistContinueBooleanUpdate()
            },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(

                    value = nameText,
                    placeholder = {
                        Text(
                            modifier = Modifier.height(23.dp),
                            text = "닉네임을 입력해주세요",
                            fontSize = SisoFontSizeTokens.Label1,
                            color = SisoColorTokens.GrayScale50
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
                // 숫자만 입력
                val tempText = if (input.length >= 3) {
                    input.substring(0 until 3)
                } else {
                    input
                }
                ageText = tempText.replace(Regex("[^0-9]"), "")
                viewModel.ageUpdate(ageText)
                viewModel.fistContinueBooleanUpdate()
            },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(

                    value = ageText,
                    placeholder = {
                        Text(
                            modifier = Modifier.height(23.dp),
                            text = "나이를 입력해주세요",
                            fontSize = SisoFontSizeTokens.Label1,
                            color = SisoColorTokens.GrayScale50
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
            text = "내 성별",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))

        Row {
            RepeatRadioButton(viewModel.myRadioButtons) {
                viewModel.fistContinueBooleanUpdate()
            }
        }
        Spacer(modifier = Modifier.size(size = 28.dp))
        Text(
            text = "매칭 성별",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 6.dp))
        Text(
            text = "동성선택시 동성친구 이성선택시 이성친구를\n추천해 드려요.",
            style = SisoTypoTokens.Label1,
            color = SisoColorTokens.GrayScale50
        )
        Spacer(modifier = Modifier.size(size = 12.dp))

        Row {
            RepeatRadioButton(viewModel.pairRadioButtons) {
                viewModel.fistContinueBooleanUpdate()
            }
        }

        Spacer(modifier = Modifier.size(size = 30.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SisoColorTokens.Gold40,
                disabledContainerColor = SisoColorTokens.GrayScale30
            ),
            onClick = {
                onNavigateNext()
            },

            enabled = fistContinueBoolean.value
        ) {
            Text(
                text = "계속하기",
                style = SisoTypoTokens.Button1,

                color = if(fistContinueBoolean.value == true)SisoColorTokens.GrayScale90
                else SisoColorTokens.GrayScale50,
            )
        }
        Spacer(modifier = Modifier.size(size = 58.dp))
    }
}


@Composable
fun RepeatRadioButton(
    radios: MutableList<Pair<String, Boolean>>,
    click: () -> Unit = {}
) {
    radios.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(width = 85.dp, height = 24.dp)
                .clickable {
                    radios.replaceAll {
                        it.copy(
                            second = (it.first == info.first)
                        )
                    }
                    click()
                }
                .padding(end = 24.dp)
        ) {
            Text(
                text = info.first,
                style = SisoTypoTokens.Body2,
            )
            Spacer(modifier = Modifier.size(size = 2.dp))
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
                    click()
                }
            )

        }
    }
}

@Composable
@Preview
fun FirstLoginInfoPreview() {
    Surface(color = SisoColorTokens.White) {
        FirstLoginInfoScreen(FakeFirstLoginInfoScreenViewModel(), {})
    }

}