package com.ieum.data.mapper

import com.ieum.data.network.model.post.DietDto
import com.ieum.data.network.model.post.PostImageDto
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.PostWellnessRequest

suspend fun PostWellnessRequest.asBody(): PostWellnessRequestBody =
    PostWellnessRequestBody(
        diagnosis = diagnosis.key,
        mood = mood.key,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken ?: false,
        diet = (diet ?: Diet.DEFAULT).toDto(),
        memo = memo,
        images = imageList.mapNotNull { it.toDto() }.ifEmpty { null },
        shared = shared,
    )

private fun Diet.toDto(): DietDto =
    DietDto(
        amountEaten = amountEaten.key,
        mealContent = mealContent,
    )

private suspend fun ImageSource.Local.toDto(): PostImageDto.ForRequest? {
    val base64Data = Base64Encoder.encode(file).getOrNull() ?: return null
    return PostImageDto.ForRequest(
        filename = file.name,
        base64Data = base64Data,
    )
}