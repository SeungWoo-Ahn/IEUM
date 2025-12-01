package com.ieum.presentation.mapper

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

sealed interface DateFormatStrategy {
    fun format(): String

    data class FullDate(private val timestamp: Int) : DateFormatStrategy {
        private val pattern = "yyyy년 MM월 dd일"

        override fun format(): String {
            val seconds = timestamp.toLong()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val instant = Instant.ofEpochSecond(seconds)
                val dateTime = instant.atZone(ZoneId.systemDefault())
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA)
                dateTime.format(formatter)
            } else {
                val milliseconds = seconds * 1_000
                val date = Date(milliseconds)
                val formatter = SimpleDateFormat(pattern, Locale.KOREA)
                formatter.format(date)
            }
        }
    }

    data object Today : DateFormatStrategy {
        override fun format(): String {
            val current = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            return formatter.format(current)
        }
    }
}

fun formatDate(strategy: DateFormatStrategy): String = strategy.format()
