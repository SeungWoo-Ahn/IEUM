package com.ieum.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
        WHERE isMine = 1 AND type = :type
        ORDER BY createdAt DESC
    """)
    fun getMyPostPagingSource(type: String): PagingSource<Int, PostEntity>

    @Query("""
        SELECT * FROM posts
        WHERE userId = :userId
        ORDER BY createdAt DESC
    """)
    fun getOthersPostPagingSource(userId: Int): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Update
    suspend fun update(post: PostEntity)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPostList()

    @Query("DELETE FROM posts WHERE isMine = 1 AND type = :type")
    suspend fun deleteMyPostList(type: String)

    @Query("DELETE FROM posts WHERE userId = :userId")
    suspend fun deleteOthersPostList(userId: Int)

    @Query("DELETE FROM posts WHERE id = :id AND type = :type")
    suspend fun deleteById(id: Int, type: String)

    @Query("""
        UPDATE posts
        SET isLiked = 1
        WHERE id = :id AND type = :type
    """)
    suspend fun likePost(id: Int, type: String)

    @Query("""
        UPDATE posts
        SET isLiked = 0
        WHERE id = :id AND type = :type
    """)
    suspend fun unLikePost(id: Int, type: String)
}