package com.ieum.domain.model.post

import com.ieum.domain.model.base.KeyAble

enum class PostType(override val key: String) : KeyAble<String> {
    ALL("all"),
    WELLNESS("wellness"),
    DAILY("daily");
}