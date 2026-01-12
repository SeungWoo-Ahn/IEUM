package com.ieum.data.database.dao

import androidx.room.Dao

@Dao
interface PostDao {
    fun getAllPostListPagingSource()
}