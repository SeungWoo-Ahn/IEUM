package com.ieum.domain.model.user

data class Surgery(
    val date: String,
    val description: String,
) : Comparable<Surgery> {
    override fun compareTo(other: Surgery): Int =
        when {
            date != other.date -> date.compareTo(other.date)
            else -> description.compareTo(other.description)
        }
}
