package com.ieum.domain.model.user

data class RadiationTherapy(
    val startDate: String,
    val endDate: String?,
) : Comparable<RadiationTherapy> {
    override fun compareTo(other: RadiationTherapy): Int =
        when {
            startDate != other.startDate -> startDate.compareTo(other.startDate)
            endDate != null && other.endDate != null -> endDate.compareTo(other.endDate)
            endDate != null -> 1
            other.endDate != null -> -1
            else -> 0
        }
}
