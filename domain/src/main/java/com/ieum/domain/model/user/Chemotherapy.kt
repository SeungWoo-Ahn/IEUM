package com.ieum.domain.model.user

data class Chemotherapy(
    val cycle: Int,
    val startDate: String, // TODO: endDate 추가
)
