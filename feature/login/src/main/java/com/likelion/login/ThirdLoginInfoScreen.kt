package com.likelion.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CommonActiveButton
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens

@Composable
fun ThirdLoginInfoScreen(
    viewModel: ThirdLoginInfoScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(size = 8.dp))
        // Step indicator
        AsyncImage(
            model = com.likelion.ui.R.drawable.img_circle_bar_login3,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.size(size = 27.dp))
        Text("나를 표현하는 사진을 보여주세요", style = SisoTypoTokens.Title2)
        Spacer(modifier = Modifier.size(size = 8.dp))
        Text("최소 1장 이상 선택해주세요\n정보는 나중에 수정할 수 있어요", style = SisoTypoTokens.Body4, color = SisoColorTokens.GrayScale60)
        Spacer(modifier = Modifier.size(size = 44.dp))
        AsyncImage(model = com.likelion.ui.R.drawable.img_photo_holder, contentDescription = "")
        Spacer(modifier = Modifier.size(size = 140.dp))
        CommonActiveButton("사진 추가하기", {})
        Spacer(modifier = Modifier.size(size = 72.dp))

    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ThirdLoginInfoScreenPreview() {
    SisoTheme {
        ThirdLoginInfoScreen()
    }

}

// 제공된 이미지에 대한 가상의 drawable 리소스
// 실제 프로젝트에서는 여러분의 R.drawable 경로를 사용하세요.
// 예시: R.drawable.sample_image_1, R.drawable.sample_image_2, etc.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoUploadBottomSheet(
    onDismissRequest: () -> Unit // 바텀 시트를 닫을 때 호출되는 함수
) {
    val sheetState = rememberModalBottomSheetState()

/*    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null, // 드래그 핸들 제거
        containerColor = Color.White
    ) {*/
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
                        painter = painterResource(id = com.likelion.ui.R.drawable.ic_close_24px),
                        contentDescription = "Close"
                    )
                }
            }

            // 도움말 텍스트
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "꼭 얼굴이 아니더라도 내가 관심있는 분야의\n사진을 올려줘도 좋아요",
                style = SisoTypoTokens.Body4,
                color = SisoColorTokens.GrayScale60
            )
            Text(
                text = "예시) 반려동물, 꽃, 운동하는 사진, 등산 등",
                fontSize = 16.sp,
                color = Color.Gray
            )

            // 예시 이미지들
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // 이 부분을 실제 이미지 리소스로 변경하세요.
                // Image(
                //     painter = painterResource(id = R.drawable.sample_image_1),
                //     contentDescription = null,
                //     modifier = Modifier
                //         .size(96.dp)
                //         .clip(RoundedCornerShape(8.dp)),
                //     contentScale = ContentScale.Crop
                // )
                // ... 나머지 이미지들
                AsyncImage(model = com.likelion.ui.R.drawable.img_dog, contentDescription = "", modifier = Modifier.size(105.dp,106.dp))
                AsyncImage(model = com.likelion.ui.R.drawable.img_flower, contentDescription = "",modifier = Modifier.size(105.dp,106.dp))
                AsyncImage(model = com.likelion.ui.R.drawable.img_baseball, contentDescription = "",modifier = Modifier.size(105.dp,106.dp))

                
            }

            // 하단 버튼
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onDismissRequest, // 버튼 클릭 시 바텀 시트 닫기
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDD835), // 노란색 계열
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "사진 추가하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // 하단 패딩
        }

}

// 프리뷰
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PhotoUploadBottomSheetPreview() {
    SisoTheme { // 테마 적용
        // 프리뷰에서는 상태를 관리하지 않으므로 임시로 onDismissRequest를 {}로 설정
        PhotoUploadBottomSheet(onDismissRequest = {})
    }
}