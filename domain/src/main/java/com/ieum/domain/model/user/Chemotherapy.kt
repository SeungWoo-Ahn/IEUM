package com.ieum.domain.model.user

data class Chemotherapy(
    val cycle: Int,
    val startDate: String,
    val endDate: String?,
) : Comparable<Chemotherapy> {
    override fun compareTo(other: Chemotherapy): Int =
        when {
            cycle != other.cycle -> cycle - other.cycle
            startDate != other.startDate -> startDate.compareTo(other.startDate)
            endDate != null && other.endDate != null -> endDate.compareTo(other.endDate)
            endDate != null -> 1
            other.endDate != null -> -1
            else -> 0
        }
}
