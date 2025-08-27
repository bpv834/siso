package com.likelion.home.navigation

import androidx.lifecycle.SavedStateHandle
import java.io.Serializable

data class Pub(
    val chipText : String
): Serializable

fun SavedStateHandle.getString(key:String):String?
    = get<String>(key)
