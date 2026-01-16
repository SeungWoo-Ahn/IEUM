package com.ieum.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import com.ieum.data.network.model.post.DietDto

@Entity(
    tableName = "posts",
    primaryKeys = ["id", "type"]
)
data class PostEntity(
    val id: Int,
    val type: String,
    val userId: Int? = null,
    val userNickname: String? = null,
    val diagnosis: List<String>? = null,
    val mood: Int? = null,
    val unusualSymptoms: String? = null,
    val medicationTaken: Boolean? = null,
    @Embedded(prefix = "_diet") val diet: DietDto? = null,
    val memo: String? = null,
    val title: String? = null,
    val content: String? = null,
    val images: List<String>? = null,
    val shared: Boolean,
    val isLiked: Boolean,
    val isMine: Boolean,
    val createdAt: Int,
)
