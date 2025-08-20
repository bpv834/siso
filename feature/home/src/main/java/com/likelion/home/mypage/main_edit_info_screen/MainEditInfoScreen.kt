package com.likelion.home.mypage.main_edit_info_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.likelion.ui.R
import com.likelion.ui.component.outlined_textfield.CommonOutlinedTextFiled
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun MainEditInfoScreen(
    viewModel: MainEditInfoScreenViewModelType
) {
    var introduceText by remember { mutableStateOf("") }
    val introduceTextRange = TextRange(0,50)
    var ageText by remember { mutableStateOf("") }
    val ageTextRange = TextRange(0,3)

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable(
                        interactionSource = remember{ MutableInteractionSource() },
                        indication = null
                    ){

                    }
            ){
                AsyncImage(
                    modifier = Modifier.size(120.dp, 120.dp),
                    model = R.drawable.example_profile,
                    contentDescription = ""
                )
                Box(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .offset((2).dp,(3).dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(SisoColorTokens.GrayScale5),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.size(28.dp)
                            .clip(CircleShape)
                            .background(SisoColorTokens.GrayScale20),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = SisoColorTokens.GrayScale60,
                            painter = rememberAsyncImagePainter(R.drawable.text_edit),
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
            color = SisoColorTokens.GrayScale50,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(8.dp))
        // 여기에 EditText(텍스트 필드) 추가
        CommonOutlinedTextFiled(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            placeholderText = "닉네임을 입력해주세요",
            value = introduceText, // collect된 실시간 변경된 스트링 값을 넣는다.
            onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                introduceText = newText.substring(introduceTextRange)
            }
        )
        Spacer(Modifier.size(24.dp))
        Text(
            modifier = Modifier.height(24.dp),
            text = "나이",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            // 여기에 EditText(텍스트 필드) 추가
            CommonOutlinedTextFiled(
                modifier = Modifier
                    .size(86.dp,52.dp)
                    .padding(end = 6.dp),
                placeholderText = "",
                value = ageText, // collect된 실시간 변경된 스트링 값을 넣는다.
                onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                    ageText = newText.substring(ageTextRange)
                }
            )
            Text(
                modifier = Modifier.height(24.dp),
                text = "세",
                style = SisoTypoTokens.SubTitle1,
                color = SisoColorTokens.GrayScale50,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.size(24.dp))
        Text(
            modifier = Modifier.height(24.dp),
            text = "자기소개",
            style = SisoTypoTokens.SubTitle1,
            color = SisoColorTokens.GrayScale50,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.height(44.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(999.dp))
                .drawWithContent{
                    drawRect(SisoColorTokens.GrayScale60)
                }
        ) {

        }
        Box(
            modifier = Modifier.padding(top = 10.dp,
                start = 16.dp,end = 16.dp)
                .height(658.dp).fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(SisoColorTokens.GrayScale20),
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp,
                    start = 16.dp,end = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.size(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = "닉네임",
                        style = SisoTypoTokens.SubTitle1,
                        color = SisoColorTokens.GrayScale50
                    )
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        tint = SisoColorTokens.GrayScale60,
                        painter = rememberAsyncImagePainter(R.drawable.text_edit),
                        contentDescription = ""
                    )
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    modifier = Modifier.height(58.dp).fillMaxWidth(),
                    text = "닉네임최대몇글자로하나요궁금해요",
                    style = SisoTypoTokens.Title2,
                    color = SisoColorTokens.GrayScale90
                )
                Spacer(Modifier.size(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = "나이",
                        style = SisoTypoTokens.SubTitle1,
                        color = SisoColorTokens.GrayScale50
                    )
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        tint = SisoColorTokens.GrayScale60,
                        painter = rememberAsyncImagePainter(R.drawable.text_edit),
                        contentDescription = ""
                    )
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    modifier = Modifier.height(26.dp).fillMaxWidth(),
                    text = "56세",
                    style = SisoTypoTokens.Title3,
                    color = SisoColorTokens.GrayScale70
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier.height(22.dp).fillMaxWidth(),
                    text = "자기소개",
                    style = SisoTypoTokens.Label1,
                    color = SisoColorTokens.GrayScale50
                )
                Spacer(Modifier.size(16.dp))


                Spacer(Modifier.size(16.dp))
                Box{

                    // 여기에 EditText(텍스트 필드) 추가
                    CommonOutlinedTextFiled(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(206.dp),
                        placeholderText = "안녕하세요. 인생의 황혼기에 접어들었지만, 늘 새로운 경험과 사랑을 찾아 나아가고 있습니다. 서로를 이해하며 함께할 수 있는 분을 기다립니다.",
                        value = introduceText, // collect된 실시간 변경된 스트링 값을 넣는다.
                        placeholderColor = SisoColorTokens.GrayScale90,
                        fieldBackColor = SisoColorTokens.White,
                        onValueChange = { newText -> // 새롭게 변경된 문자를 넘겨줌
                            introduceText = newText.substring(introduceTextRange)
                        }
                    )

                    Text(
                        modifier = Modifier.height(22.dp).fillMaxWidth()
                            .align(Alignment.BottomEnd),
                        text = "${introduceText.length}/${introduceTextRange.end}",
                        style = SisoTypoTokens.Label1,
                        color = SisoColorTokens.GrayScale50
                    )

                }

            }
            }


    }
}

@Preview
@Composable
fun MainEditInfoScreenPreview(){
    SisoTheme {
        Scaffold {
            it
            MainEditInfoScreen(viewModel = FakeMainEditInfoScreenViewModel())
        }
    }
}