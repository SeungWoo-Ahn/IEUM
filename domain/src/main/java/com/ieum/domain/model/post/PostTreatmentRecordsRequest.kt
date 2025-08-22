package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

data class PostTreatmentRecordsRequest(
    val specificSymptoms: String?,
    val takingMedicine: Boolean,
    val dietary: Dietary,
    val memo: String,
    val imageList: List<ImageSource>,
    val shareCommunity: Boolean,
)
