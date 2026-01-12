package com.ieum.data.database.util

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.json.Json

class PostTypeConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { json.encodeToString(it) }

    @TypeConverters
    fun toStringList(value: String?): List<String>? = value?.let { json.decodeFromString(it) }
}