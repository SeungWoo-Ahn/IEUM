package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

data class PostDailyRequest(
    val title: String,
    val content: String,
    val imageList: List<ImageSource.Local>,
    val shared: Boolean,
)
