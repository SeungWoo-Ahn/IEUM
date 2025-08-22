package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

sealed interface Post {
    val id: Int

    data class TreatmentRecords(
        override val id: Int,
        val specificSymptoms: String?,
        val takingMedicine: Boolean,
        val dietary: Dietary,
        val memo: String?,
        val imageList: List<ImageSource>,
        val shareCommunity: Boolean,
    ) : Post

    data class DailyRecords(
        override val id: Int,
        val title: String,
        val story: String?,
        val imageList: List<ImageSource>,
        val shareCommunity: Boolean,
    ) : Post
}