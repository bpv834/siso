package com.likelion.ui.component.photo_layout

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.theme.SisoColorTokens

@Composable
fun PhotoLayoutWith1Main4Sub(
    mainWith: Dp? = null,
    mainHeight: Dp? = null,
    subWith: Dp,
    subHeight: Dp,
    capturedImages: List<Bitmap>,
) {
    // 대표 사진
    val mainPhoto = capturedImages.firstOrNull()

    // 서브 사진 (최대 4장)
    val subPhotos = capturedImages.drop(1).take(4)

    val cameraHolder = com.likelion.ui.R.drawable.img_photo_holder

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 대표 사진 영역
        if (mainPhoto != null) {
            Image(
                bitmap = mainPhoto.asImageBitmap(),
                contentDescription = "Main Photo",
                modifier = Modifier
                    .run {
                        if (mainWith == null) fillMaxWidth() else width(mainWith)
                    }
                    .height(mainHeight ?: 200.dp) // 너비와 관계없이 높이를 항상 적용
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            // 대표 사진이 없을 경우 홀더 이미지 표시
            AsyncImage( // 홀더 이미지는 리소스 ID이므로 AsyncImage 사용
                model = cameraHolder,
                contentDescription = "Main Photo Placeholder",
                modifier = Modifier
                    .run {
                        if (mainWith == null) fillMaxWidth() else size(mainWith, mainHeight ?: 200.dp)
                    }
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 서브 사진 영역 (LazyRow)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 실제 서브 사진들
            items(subPhotos) { bitmap ->
                Image( // Bitmap을 표시하므로 Image 사용
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Sub Photo",
                    modifier = Modifier
                        .size(subWith, subHeight).border(2.dp, SisoColorTokens.Black)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // 부족한 서브 사진을 홀더 이미지로 채우기
            val remainingSlots = 4 - subPhotos.size
            if (remainingSlots > 0) {
                items(remainingSlots) {
                    AsyncImage( // 홀더 이미지는 리소스 ID이므로 AsyncImage 사용
                        model = cameraHolder,
                        contentDescription = "Sub Photo Placeholder",
                        modifier = Modifier
                            .size(subWith, subHeight)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}