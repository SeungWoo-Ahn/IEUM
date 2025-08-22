package com.ieum.domain.model.post

enum class DietaryStatus {
    EAT_WELL,
    EAT_SMALL_AMOUNTS,
    EAT_NOTHING,
}

data class Dietary(
    val status: DietaryStatus,
    val content: String,
)