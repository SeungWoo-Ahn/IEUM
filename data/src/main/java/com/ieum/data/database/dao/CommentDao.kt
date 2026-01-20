package com.ieum.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ieum.data.database.model.CommentEntity

@Dao
interface CommentDao {
    @Query("""
        SELECT * FROM comments
        ORDER BY createdAt DESC
    """)
    fun getCommentPagingSource(): PagingSource<Int, CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: CommentEntity)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteById(id: Int)
}