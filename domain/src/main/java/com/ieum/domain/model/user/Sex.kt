package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class Sex(override val key: String) : KeyAble<String> {
    MALE("male"),
    FEMALE("female");

    companion object {
        private val map = entries.associateBy(Sex::key)

        fun fromKey(key: String): Sex = map[key]
            ?: throw IllegalArgumentException("Invalid Sex key: $key")
    }
}