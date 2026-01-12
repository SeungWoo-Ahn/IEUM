package com.ieum.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ieum.data.database.dao.PostDao
import com.ieum.data.database.model.PostEntity
import com.ieum.data.database.util.PostTypeConverters

@Database(
    entities = [
        PostEntity::class
    ],
    version = 1
)
@TypeConverters(PostTypeConverters::class)
abstract class IeumDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}