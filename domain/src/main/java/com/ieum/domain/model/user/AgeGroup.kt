package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class AgeGroup(override val key: String) : KeyAble<String> {
    UNDER_THIRTY("under30s"),
    FORTIES("40s"),
    FIFTIES("50s"),
    SIXTIES("60s"),
    OVER_SEVENTY("over70s");

    companion object {
        private val map = entries.associateBy(AgeGroup::key)

        fun fromKey(key: String): AgeGroup = map[key]
            ?: throw IllegalArgumentException("Invalid AgeGroup key: $key")
    }
}