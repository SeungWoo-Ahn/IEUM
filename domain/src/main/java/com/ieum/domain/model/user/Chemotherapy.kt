package com.ieum.domain.model.user

data class Chemotherapy(
    val cycle: Int,
    val startDate: String,
    val endDate: String?,
)
