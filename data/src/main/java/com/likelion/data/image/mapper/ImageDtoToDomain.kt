package com.likelion.data.image.mapper

import com.likelion.domain.image.model.ImageModel
import com.likelion.remote.model.response.ImageResponse



// 이 매퍼 함수를 클래스 내부에 정의하면 private으로 캡슐화할 수 있습니다.
fun ImageResponse.toDomain(): ImageModel {
    return ImageModel(
        imagePath = this.path,
        imageId = this.id
    )
}
