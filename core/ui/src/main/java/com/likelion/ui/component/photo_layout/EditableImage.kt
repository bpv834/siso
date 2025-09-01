package com.likelion.ui.component.photo_layout

import java.io.Serializable

data class EditableImage(
    val original: ImageItem?,        // Main에서 받은 원본
    var edited: ImageItem?   // Edit 화면에서 수정된 이미지
) : Serializable