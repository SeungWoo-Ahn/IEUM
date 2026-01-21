package com.ieum.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
)
data class CommentEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val nickname: String,
    val content: String,
    val createdAt: Int,
    val isMine: Boolean,
)
