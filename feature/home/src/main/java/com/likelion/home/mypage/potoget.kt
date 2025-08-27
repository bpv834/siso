package com.likelion.home.mypage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

// uri를 비트맵으로 변환하는 확장 함수
fun Uri.getBitmap(context: Context): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}

// URL → Bitmap
fun String.getBitmapFromUrl(): Bitmap? {
    return try {
            val input: InputStream = URL(this@getBitmapFromUrl).openStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

}


suspend fun String.calculateImageHash(): String {
    return withContext(Dispatchers.IO) {
        val bitmap = this@calculateImageHash.getBitmapFromUrl()!!
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(bytes)
        digest.joinToString("") { "%02x".format(it) }
    }
}

suspend fun String.isPlayableAudioUrl(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(this@isPlayableAudioUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()

            val contentType = connection.contentType // ex: "audio/mpeg", "audio/wav"
            connection.disconnect()

            contentType?.startsWith("audio/") == true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}