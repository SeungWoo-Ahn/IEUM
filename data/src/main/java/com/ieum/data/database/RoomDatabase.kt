package com.ieum.data.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.ieum.data.database.model.PostEntity
import com.ieum.data.database.util.PostTypeConverters

@Database(
    entities = [
        PostEntity::class
    ],
    version = 1
)
@TypeConverters(PostTypeConverters::class)
class RoomDatabase {
}