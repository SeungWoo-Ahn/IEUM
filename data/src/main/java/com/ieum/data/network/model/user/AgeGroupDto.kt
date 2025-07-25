package com.ieum.data.network.model.user

enum class AgeGroupDto(val key: String) {
    UNDER_THIRTY("under30"),
    FORTIES("40s"),
    FIFTIES("50s"),
    SIXTIES("60s"),
    OVER_SEVENTY("over70");

    companion object {
        private val map = entries.associateBy(AgeGroupDto::key)

        fun fromKey(key: String): AgeGroupDto = map[key]
            ?: throw IllegalArgumentException("Invalid AgeGroupDtoKey key: $key")
    }
}