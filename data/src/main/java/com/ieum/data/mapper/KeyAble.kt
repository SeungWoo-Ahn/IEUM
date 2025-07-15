package com.ieum.data.mapper

internal interface KeyAble<K, V> {
    fun toKey(value: V): K

    fun fromKey(key: K): V
}