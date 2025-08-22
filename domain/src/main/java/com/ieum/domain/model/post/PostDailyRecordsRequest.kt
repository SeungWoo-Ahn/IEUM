package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

data class PostDailyRecordsRequest(
    val title: String,
    val story: String?,
    val imageList: List<ImageSource>,
    val shareCommunity: Boolean,
)
