package com.ieum.data.mapper

internal interface KeyAble<T> {
    fun toKey(value: T): String

    fun fromKey(key: String): T
}