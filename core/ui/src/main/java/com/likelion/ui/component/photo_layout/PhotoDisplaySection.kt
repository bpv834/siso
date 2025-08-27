package com.likelion.ui.component.photo_layout

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.likelion.ui.component.button.CommonButtonWithState
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

// 사진목록들을 보여주고 사진 추가하기 버튼을 누르면 뷰모델 바텀시트 상태 변수를 true로 만들어주는 화면
@Composable
fun PhotoDisplaySection(
    capturedImages: List<Bitmap>,
    onDelete: (Bitmap) -> Unit,
    onAddButtonClick: () -> Unit
) {
    Text(text = "대표사진", style = SisoTypoTokens.SubTitle1, color = SisoColorTokens.Gray60)
    Spacer(modifier = Modifier.size(size = 9.dp))
    PhotoLayoutWith1Main4Sub(
        mainWith = null,
        mainHeight = 206.dp,
        subWith = 76.dp,
        subHeight = 72.dp,
        capturedImages = capturedImages,
        onClickDelete = { bitmap -> onDelete(bitmap) }
    )
    Spacer(modifier = Modifier.size(size = 37.dp))
    // 사진이 5개이상이면 비활성화 하는 상태변수
    // derivedStateOf는 다른 State 변수에 의존할 때만 작동합니다.
    // 따라서 derivedStateOf는 말고 변수를 계속 할당해주는 코드로 변경
    val isAddCapture = capturedImages.size < 5
  //  Log.d("버튼 상태", "capturedImages.size: ${capturedImages.size}, isAddCapture: $isAddCapture")
    CommonButtonWithState(
        text = "사진 추가하기 (${capturedImages.size}/5)",
        onClick = { onAddButtonClick() },
        isActive = isAddCapture
    )
}