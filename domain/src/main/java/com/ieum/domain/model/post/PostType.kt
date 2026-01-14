package com.ieum.domain.model.post

import com.ieum.domain.model.base.KeyAble
import java.lang.IllegalArgumentException

enum class PostType(override val key: String) : KeyAble<String> {
    WELLNESS("wellness"),
    DAILY("daily");

    companion object {
        private val map = entries.associateBy(PostType::key)

        fun fromKey(key: String): PostType = map[key]
            ?: throw IllegalArgumentException("Invalid PostType key: $key")
    }
}