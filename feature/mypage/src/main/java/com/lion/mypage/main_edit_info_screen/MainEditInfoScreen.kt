package com.lion.mypage.main_edit_info_screen

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme

@Composable
fun MainEditInfoScreen(
    viewModel: MainEditInfoScreenViewModelType
) {
    Column {
        Column(
            modifier = Modifier.padding(top = 10.dp,
                start = 16.dp,end = 16.dp)
                .height(658.dp).fillMaxWidth()
                .background(SisoColorTokens.GrayScale20),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 24.dp)
                    .size(120.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    modifier = Modifier.size(120.dp, 120.dp),
                    model = R.drawable.example_profile,
                    contentDescription = ""
                )
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .size(34.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = SisoColorTokens.GrayScale5,
                        contentColor = SisoColorTokens.GrayScale60
                    ),
                    onClick = {

                    }
                ) {
                    Box(
                        modifier = Modifier.size(28.dp)
                            .background(SisoColorTokens.GrayScale20)
                            .clip(CircleShape),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = rememberAsyncImagePainter(R.drawable.text_edit),
                            contentDescription = ""
                        )
                    }

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