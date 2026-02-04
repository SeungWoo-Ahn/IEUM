package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetMonthlyWellnessListResponse(
    val posts: List<MonthlyWellnessDto>
)
