package com.likelion.ui.component.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.R
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens


// 💡 PhotoUploadBottomSheet Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PhotoUploadBottomSheetPreview() {
    SisoTheme {
        PhotoUploadBottomSheet(
            onDismissRequest = {},
            onTakePhotoClick = {},
            onPickFromGalleryClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoUploadBottomSheet(
    onDismissRequest: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPickFromGalleryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        // 상단 헤더 (제목과 닫기 버튼)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "사진 업로드 도움말",
                style = SisoTypoTokens.Title2
            )
            IconButton(onClick = onDismissRequest) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_24px),
                    contentDescription = "Close"
                )
            }
        }

        // 도움말 텍스트
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "꼭 얼굴이 아니더라도 내가 관심있는 분야의\n사진을 올려줘도 좋아요",
            style = SisoTypoTokens.Body4,
            color = SisoColorTokens.Gray60
        )
        Text(
            text = "예시) 반려동물, 꽃, 운동하는 사진, 등산 등",
            style = SisoTypoTokens.Body4,
            color = Color.Gray
        )

        // 예시 이미지들
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = R.drawable.img_dog,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = R.drawable.img_flower,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
            AsyncImage(
                model = R.drawable.img_baseball,
                contentDescription = "",
                modifier = Modifier.size(105.dp, 106.dp)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
        // 하단 버튼
        CommonActiveButton("카메라로 사진찍기", onClick = onTakePhotoClick)
        Spacer(modifier = Modifier.height(8.dp))
        CommonActiveButton("앨범에서 가져오기", onClick = onPickFromGalleryClick)
        Spacer(modifier = Modifier.height(16.dp)) // 하단 패딩
    }
}
