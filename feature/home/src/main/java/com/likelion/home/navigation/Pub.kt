package com.likelion.home.navigation

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import java.io.Serializable

data class Pub(
    val chipText : String
): Serializable

fun SavedStateHandle.getString(key:String):String?{
    d("key",key)
    val value = get<String>(key)
    d("key",value ?: "null")
    return value
}

