package com.likelion.ui.component.photo_layout

import coil3.Bitmap
import java.io.Serializable
import java.util.UUID

sealed class ImageItem(
    open val id: String =  UUID.randomUUID().toString()
): Serializable {
    data class UrlImage(
        val url: String,
        override val id: String = UUID.randomUUID().toString()
    ) : ImageItem(id) , Serializable

    data class BitmapImage(
        val bitmap: Bitmap,
        override val id: String = UUID.randomUUID().toString()
    ) : ImageItem(id) , Serializable
}