package com.likelion.domain.voice.model

import com.google.gson.annotations.SerializedName

data class VoiceSampleModel (
    val id: Long =0L,
    val url: String="",
    val duration: Int
){

}