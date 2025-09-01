package com.likelion.home.chat.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults.filledIconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.domain.chat.model.MyChat
import com.likelion.domain.chat.model.PartnerChat
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    query: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    placeholderText: String = "메세지 보내기",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = query,
            onValueChange = onQueryUpdate,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(color = SisoColorTokens.Gray20, shape = RoundedCornerShape(24.dp)),
            textStyle = SisoTypoTokens.Body4,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .heightIn(min = 51.dp, max = 160.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(SisoColorTokens.Gray20)
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = placeholderText.ifEmpty { "메시지 보내기" },
                                style = SisoTypoTokens.Body4,
                                color = SisoColorTokens.Gray50,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (query.isNotBlank()) onEnter()
                }
            )
        )

        Spacer(modifier = Modifier.size(16.dp))

        FilledIconButton(
            onClick = { onEnter() },
            enabled = query.isNotBlank(),
            colors = filledIconButtonColors(
                containerColor = SisoColorTokens.Gold30,
                contentColor = SisoColorTokens.Gray90,
                disabledContainerColor = SisoColorTokens.Gray20,
                disabledContentColor = SisoColorTokens.Gray50
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "보내기",
                tint = if (query.isNotBlank()) SisoColorTokens.Gray90 else SisoColorTokens.Gray40
            )
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ChatBox(chat: PartnerChat) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxBubbleWidth = screenWidth * 0.7f
    Row(
        verticalAlignment = Alignment.Bottom, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        AsyncImage(
            model = chat.partnerImg,
            contentDescription = "프로필 사진",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .widthIn(max = maxBubbleWidth)
                .background(
                    color = SisoColorTokens.Gray20,
                    shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomEnd = 14.dp)
                )
        ) {
            Text(
                text = chat.partnerMsg,
                style = SisoTypoTokens.Body3,
                // softWrap = true,
                maxLines = Int.MAX_VALUE,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
        Text(
            text = chat.partnerTime,
            style = SisoTypoTokens.Caption1,
            color = SisoColorTokens.Gray50,
            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
        )
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MyChatBox(myChat: MyChat) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxBubbleWidth = screenWidth * 0.7f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = myChat.time, style = SisoTypoTokens.Caption1, color = SisoColorTokens.Gray50, modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 6.dp, end = 8.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .widthIn(max = maxBubbleWidth)
                .background(
                    color = SisoColorTokens.Gold30,
                    shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp, topEnd = 14.dp)
                )
        ) {
            Text(
                text = myChat.msg,
                style = SisoTypoTokens.Body3,
                softWrap = true,
                color = SisoColorTokens.Gray90,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
@Preview
fun ChatBoxPreview() {
    SisoTheme {
        val text = PartnerChat(
            partnerImg = "https://picsum.photos/200/200",
            partnerMsg = "안녕하세요",
            partnerTime = "12:25",
            showTime = false
        )
        Column {
            ChatBox(text)
        }
    }
}

@Composable
@Preview
fun MyChatBoxPreview() {
    SisoTheme {

        val text = MyChat(
            chatRoomId = 0,
            msg = "이건 바로 위에 있는 텍스트 박스입니다. 시간이 보이지 않아요",
            time = "12:25",
            true
        )
        Column {
            MyChatBox(text)
        }
    }
}


@Composable
@Preview
fun ChatTextFieldPreview() {
    SisoTheme {
        Column {
            ChatTextField(
                query = "hello",
                onQueryUpdate = {}, {}
            )
            Spacer(modifier = Modifier.size(10.dp))
            ChatTextField(
                query = "",
                onQueryUpdate = {}, {}
            )
        }

    }
}