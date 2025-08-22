package com.ieum.presentation.mapper

import com.ieum.domain.model.post.Dietary
import com.ieum.domain.model.post.DietaryStatus
import com.ieum.domain.model.post.PostTreatmentRecordsRequest
import com.ieum.presentation.model.post.DietaryStatusUiModel
import com.ieum.presentation.model.post.PostTreatmentRecordsUiModel

fun DietaryStatusUiModel.toDomain(): DietaryStatus =
    when (this) {
        DietaryStatusUiModel.EAT_WELL -> DietaryStatus.EAT_WELL
        DietaryStatusUiModel.EAT_SMALL_AMOUNTS -> DietaryStatus.EAT_SMALL_AMOUNTS
        DietaryStatusUiModel.EAT_NOTHING -> DietaryStatus.EAT_NOTHING
    }

fun DietaryStatus.toUiModel(): DietaryStatusUiModel =
    when (this) {
        DietaryStatus.EAT_WELL -> DietaryStatusUiModel.EAT_WELL
        DietaryStatus.EAT_SMALL_AMOUNTS -> DietaryStatusUiModel.EAT_SMALL_AMOUNTS
        DietaryStatus.EAT_NOTHING -> DietaryStatusUiModel.EAT_NOTHING
    }

fun PostTreatmentRecordsUiModel.toRequest(): PostTreatmentRecordsRequest =
    PostTreatmentRecordsRequest(
        specificSymptoms = specificSymptoms.ifEmpty { null },
        takingMedicine = takingMedicine ?: false,
        dietary = dietary?.let { Dietary(it.status.toDomain(), it.content) } ?: Dietary(DietaryStatus.EAT_NOTHING, ""),
        memo = memo,
        imageList = imageList,
        shareCommunity = shareCommunity,
    )
