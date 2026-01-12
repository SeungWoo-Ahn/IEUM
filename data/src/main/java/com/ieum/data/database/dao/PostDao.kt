package com.ieum.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ieum.data.database.model.PostEntity

@Dao
interface PostDao {
    @Query("""
        SELECT * FROM posts
        WHERE (:diagnosis IS NULL OR diagnosis LIKE '%' || :diagnosis || '%')
        ORDER BY createdAt DESC
    """)
    fun getAllPostPagingSource(diagnosis: String?): PagingSource<Int, PostEntity>

    @Query("""
        SELECT * FROM posts
        WHERE userId = :userId AND type = :type
        ORDER BY createdAt DESC
    """)
    fun getMyPostPagingSource(userId: Int, type: String): PagingSource<Int, PostEntity>

    @Query("""
        SELECT * FROM posts
        WHERE userId = :userId
        ORDER BY createdAt DESC
    """)
    fun getOthersPostPagingSource(userId: Int): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPostList()

    @Query("DELETE FROM posts WHERE userId = :userId AND type = :type")
    suspend fun deleteMyPostList(userId: Int, type: String)

    @Query("DELETE FROM posts WHERE userId = :userId")
    suspend fun deleteOthersPostList(userId: Int)
}