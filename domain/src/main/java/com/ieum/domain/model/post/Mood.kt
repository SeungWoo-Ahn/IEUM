package com.ieum.domain.model.post

import com.ieum.domain.model.base.KeyAble

enum class Mood(override val key: Int) : KeyAble<Int> {
    HAPPY(1),
    GOOD(2),
    NORMAL(3),
    BAD(4),
    WORST(5);

    companion object {
        private val map = entries.associateBy(Mood::key)

        fun fromKey(key: Int): Mood = map[key]
            ?: throw IllegalArgumentException("Invalid Mood key: $key")
    }
}