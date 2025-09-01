package com.likelion.ui.component.photo_layout

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.likelion.ui.component.button.CircularCloseButton
import com.likelion.ui.R

@Composable
fun PhotoLayoutWith1Main4Sub(
    mainWith: Dp? = null,
    mainHeight: Dp? = null,
    subWith: Dp,
    subHeight: Dp,
    capturedImages: List<Bitmap>,
    onClickDelete: (bitmap: Bitmap) -> Unit
) {
    val mainPhoto = capturedImages.firstOrNull()
    val subPhotos = capturedImages.drop(1).take(4)
    val cameraHolder = R.drawable.img_photo_holder
    val subCameraHolder = R.drawable.img_sub_camera_holder_4unit

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 대표 사진 영역: 사진 유무에 따라 내용만 바뀝니다.
        Box(
            modifier = Modifier
                .run {
                    if (mainWith == null) fillMaxWidth() else width(mainWith)
                }
                .height(mainHeight ?: 286.dp) // 높이 통일
        ) {
            if (mainPhoto != null) {
                Image(
                    bitmap = mainPhoto.asImageBitmap(),
                    contentDescription = "Main Photo",
                    modifier = Modifier
                        .height(206.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
                CircularCloseButton(
                    onClick = { onClickDelete(mainPhoto) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 9.dp, y = (-9).dp),
                    bitmap = mainPhoto
                )
            } else {
                Column {
                    AsyncImage(
                        model = cameraHolder,
                        contentDescription = "Main Photo Placeholder",
                        modifier = Modifier
                            .fillMaxSize()
                            .height(206.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = subCameraHolder,
                        contentDescription = "Sub Photo Placeholder",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(subHeight), // 서브 사진의 높이에 맞춰 홀더 이미지 높이 지정
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(17.dp)) // 모든 경우에 동일한 간격 유지

        // 서브 사진 영역: 사진 유무에 따라 레이아웃만 바뀝니다.
        if (subPhotos.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (subPhotos.size != 4) Arrangement.spacedBy(8.dp)
                else Arrangement.SpaceBetween,
            ) {
                items(subPhotos) { bitmap ->
                    Box(
                        modifier = Modifier
                    ) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Sub Photo",
                            modifier = Modifier
                                .size(subWith, subHeight)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )
                        CircularCloseButton(
                            onClick = { onClickDelete(bitmap) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 9.dp, y = (-9).dp), bitmap = bitmap
                        )
                    }
                }
            }
        } else {
            // 서브 포토가 널일때
            if (mainPhoto == null) Column {
                AsyncImage(
                    model = subCameraHolder,
                    contentDescription = "Sub Photo Placeholder",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(subHeight), // 서브 사진의 높이에 맞춰 홀더 이미지 높이 지정
                    contentScale = ContentScale.Crop
                )
            } else {
                Spacer(modifier = Modifier.height(72.dp)) // 모든 경우에 동일한 간격 유지
            }
        }
    }
}