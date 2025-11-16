package com.ieum.presentation.model.post

import com.ieum.domain.model.image.ImageSource

data class PostWellnessUiModel(
    val mood: MoodUiModel?,
    val unusualSymptoms: String,
    val medicationTaken: Boolean?,
    val diet: DietUiModel?,
    val memo: String,
    val imageList: List<ImageSource>,
    val shared: Boolean,
) {
    fun validate(): Boolean = mood != null

    companion object {
        val EMPTY = PostWellnessUiModel(
            mood = null,
            unusualSymptoms = "",
            medicationTaken = null,
            diet = null,
            memo = "",
            imageList = emptyList(),
            shared = false,
        )
    }
}