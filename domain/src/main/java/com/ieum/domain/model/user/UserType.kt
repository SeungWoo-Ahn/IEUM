package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class UserType(override val key: String) : KeyAble<String> {
    PATIENT("patient"),
    CAREGIVER("caregiver");

    companion object {
        private val map = entries.associateBy(UserType::key)

        fun fromKey(key: String): UserType = map[key]
            ?: throw IllegalArgumentException("Invalid UserType key: $key")
    }
}