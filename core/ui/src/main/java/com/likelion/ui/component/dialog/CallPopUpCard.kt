package com.likelion.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.likelion.domain.notification.model.Call
import com.likelion.ui.R
import com.likelion.ui.component.button.CustomButtonWithIcon
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import timber.log.Timber

@Composable
fun CallPopUpCard(call: Call, onDismiss: () -> Unit, callAccept: () -> Unit, callDeny: () -> Unit) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(198.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(89.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Timber.d("콜러 프로필 = ${call.callerImage}")
                    val img =
                        call.callerImage.ifBlank { "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMzIg/MDAxNjA0MjI4ODc1Mjk4.q8rEdORC54OwRX0vaCnIvUARNo1Qv2Hfzzr271VAA7Eg.lQ_d9YJbtoAzpgI_J6Dd5tXxIBNvj6_jSB-mY7OL06cg.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%EC%B2%AD%EB%85%B9.jpg?type=w400" }
                    if (call.callerImage == "")
                        AsyncImage(
                            model = img,
                            contentDescription = "Caller profile picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "${call.callerName}님으로부터 전화가 걸려왔어요",
                        style = SisoTypoTokens.Title3,
                        color = SisoColorTokens.Gray90
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomButtonWithIcon(
                        modifier = Modifier
                            .height(45.dp)
                            .weight(1f),
                        onClick = {
                            callDeny()
                        },
                        icon = painterResource(R.drawable.ic_call_end),
                        iconTint = SisoColorTokens.White,
                        iconSize = 32.dp
                    )
                    Spacer(Modifier.size(8.dp))
                    CustomButtonWithIcon(
                        containerColor = SisoColorTokens.Green60,
                        onClick = {
                            callAccept()
                        },
                        icon = painterResource(R.drawable.ic_call),
                        iconTint = SisoColorTokens.White,
                        modifier = Modifier
                            .height(45.dp)
                            .weight(1f),
                        iconSize = 32.dp

                    )
                }
                Spacer(modifier = Modifier.height(8.dp)) // Bottom padding
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallPopUpCardPreview() {
    SisoTheme {

        val fakeCall = Call(
            callerImage = "",
            callerName = "",
            agoraToken = "",
            agoraChannel = "",
            callerId = "",
            id = ""
        )
        CallPopUpCard(
            call = fakeCall,
            onDismiss = {},
            callAccept = {},
            callDeny = {},
        )
    }
}
