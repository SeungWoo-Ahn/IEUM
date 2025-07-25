package com.ieum.data.network.model.user

enum class UserTypeDto(val key: String) {
    PATIENT("patient"),
    CAREGIVER("caregiver");

    companion object {
        private val map = entries.associateBy(UserTypeDto::key)

        fun fromKey(key: String): UserTypeDto = map[key]
            ?: throw IllegalArgumentException("Invalid UserTypeDto key: $key")
    }
}