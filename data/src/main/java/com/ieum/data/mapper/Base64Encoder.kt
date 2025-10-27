package com.ieum.data.mapper

import android.util.Base64
import android.util.Base64OutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream


object Base64Encoder {
    suspend fun encode(file: File): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val inputStream = FileInputStream(file)
            val outputStream = ByteArrayOutputStream()
            Base64OutputStream(outputStream, Base64.NO_WRAP).use { os ->
                inputStream.copyTo(os)
            }
            outputStream.toString("UTF-8")
        }
    }
}