package com.likelion.login.login_agree

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.collections.forEachIndexed

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun AgreeToTermsScreen(
    viewModel: AgreeToTermsScreenViewModelType,
    onNavigateNext: () -> Unit
) {
    val sideDp = 16.dp
    val scrollState = rememberScrollState()
    var bottomId by remember { mutableLongStateOf(0L) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val agreeList = listOf(
            1L to "(필수) 이용약관 동의",
            2L to "(선택) 마케팅 정보 수신",
    )
    val bottomTitle = listOf(
        1L to "이용약관" ,
        2L to "마케팅 정보 수신 사항",
    )
    val agreeContinueBoolean = viewModel.agreeContinueBoolean.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = sideDp, end = sideDp)
    ){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = AbsoluteAlignment.Left,

        ) {
            Spacer(modifier = Modifier.size(size = 60.dp))
            Text(
                text = "시팅에 어서오세요" +
                        "\n새로운 인연을 만나기전에"+
                        "\n동의가 필요해요",
                style = SisoTypoTokens.Body1,
                color = SisoColorTokens.Gray90,
            )
            Spacer(modifier = Modifier.size(size = 68.dp))

            AgreeRepeatRadioButton(
                termsList = agreeList,
                showBottom = {state->
                    bottomId = state
                },
                onClick = {continueBoolean->
                    viewModel.agreeContinueBooleanUpdate(continueBoolean)
                    Log.d("radioRemember", "[agreeContinueBoolean]")
                    Log.d("radioRemember", viewModel.agreeContinueBoolean.toString())
                },
            )

            Spacer(modifier = Modifier.size(size = 286.dp))

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SisoColorTokens.Gold40,
                    disabledContainerColor = SisoColorTokens.Gray30
                ),
                onClick = {
                    onNavigateNext()
                },
                enabled = agreeContinueBoolean.value
            ) {
                Text(
                    text = "계속하기",
                    style = SisoTypoTokens.Button1,
                    color = if(agreeContinueBoolean.value == true)SisoColorTokens.Gray90
                    else SisoColorTokens.Gray50,
                )
            }
            Spacer(modifier = Modifier.size(size = 56.dp))
        }
        if (bottomId != 0L) {

            ModalBottomSheet(
                modifier = Modifier.systemBarsPadding(),
                containerColor = SisoColorTokens.White,
                sheetState = sheetState,
                onDismissRequest = {
                    bottomId = 0L
                },
                dragHandle = null,
                content = {
                    Spacer(modifier = Modifier.size(size = 36.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if (bottomId == bottomTitle[0].first) bottomTitle[0].second
                            else bottomTitle[1].second,
                            style = SisoTypoTokens.Title3,
                            color = SisoColorTokens.Gray90,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align (Alignment.Center)
                                .height(29.dp)
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd) ,
                            onClick = {
                                bottomId = 0L
                            }) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(24.dp),
                                model = R.drawable.bottom_close,
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(size = if (bottomId == bottomTitle[0].first) 14.dp
                    else 32.dp))
                    DocumentScreen(sideDp, bottomId)
                }
            )
        }

    }



}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AgreeRepeatRadioButton(
    termsList: List<Pair<Long, String>>,
    showBottom: (Long)->Unit = {},
    onClick:(Boolean)->Unit = {}
){
    val checkIdList  = remember {
        mutableStateListOf<Long>()
    }

    termsList.forEachIndexed { idx, (id, text) ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(27.dp)
                .clickable (
                    indication = null,
                    interactionSource = remember{ MutableInteractionSource() },
                ){
                    if (checkIdList.contains(id)) {
                        checkIdList.remove(id)
                    } else {
                        // 비텀 바 표시
                        showBottom(id)
                        checkIdList.add(id)
                    }

                    onClick(checkIdList.contains(termsList.first().first))
                },
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = text,
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.Gray90,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd),
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
fun DocumentScreen(
    sideDp: Dp = 16.dp,
    bottomId: Long = 0L
) {
    val bottomContent = listOf(
        1L to "use_term.txt" ,
        2L to "marketing_reception_term.txt",
    )
    val content = if (bottomId == bottomContent[0].first)bottomContent[0].second
    else bottomContent[1].second
    val documentText = if (bottomId != 0L)loadTextFromFile(content = content)
                        else ""// 실제 문서는 다른 곳에서 가져와야 함

    Column(
        modifier = Modifier.padding(start = sideDp, end = sideDp)
            .verticalScroll(rememberScrollState())
    ) {
        AndroidView(
            factory = {
                TextView(it).apply {
                    text = documentText
                }
            },
            update = { textView ->
                textView.text = documentText
            },
            modifier = Modifier
        )
    }
}

@Composable
fun loadTextFromFile(
    context: Context = LocalContext.current,
    content: String = ""
): String {
    var text = ""
    try {
        val inputStream = context.assets.open(content) // your_file.txt를 실제 파일 이름으로 변경
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
            stringBuilder.append('\n')
        }
        text = stringBuilder.toString()
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return text
}

@Composable
@Preview
fun AgreeToTermsScreenPreview(){
    Surface(
        color = SisoColorTokens.White
    ){
        AgreeToTermsScreen(FakeAgreeToTermsScreenViewModel(),{})
    }

}