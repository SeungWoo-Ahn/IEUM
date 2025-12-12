package com.ieum.presentation.mapper

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
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
                val dateTime = Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault())
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
        private const val PATTERN = "yyyy-MM-dd"

        override fun format(): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern(PATTERN)
                current.format(formatter)
            } else {
                val current = Date()
                val formatter = SimpleDateFormat(PATTERN, Locale.KOREA)
                formatter.format(current)
            }
        }
    }
}

fun formatDate(strategy: DateFormatStrategy): String = strategy.format()
