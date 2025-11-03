package com.ieum.domain.model.post

import com.ieum.domain.model.base.KeyAble

enum class AmountEaten(override val key: String) : KeyAble<String> {
    WELL("well_eaten"),
    SMALL("small_amount"),
    BARELY("barely_eaten");

    companion object {
        private val map = entries.associateBy(AmountEaten::key)

        fun fromKey(key: String): AmountEaten = map[key]
            ?: throw IllegalArgumentException("Invalid AmountEaten key: $key")
    }
}