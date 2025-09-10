package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class DietDto(
    val amountEaten: String,
    val mealContent: String?,
)
