package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

data class PostWellnessRequest(
    val mood: Mood,
    val unusualSymptoms: String?,
    val medicationTaken: Boolean,
    val diet: Diet?,
    val memo: String?,
    val imageList: List<ImageSource.Local>,
    val shared: Boolean,
)
