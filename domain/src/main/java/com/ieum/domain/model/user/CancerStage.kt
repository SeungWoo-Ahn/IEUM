package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class CancerStage(override val key: Int) : KeyAble<Int> {
    STAGE_1(1),
    STAGE_2(2),
    STAGE_3(3),
    STAGE_4(4);

    companion object {
        private val map = entries.associateBy(CancerStage::key)

        fun fromKey(key: Int): CancerStage = map[key]
            ?: throw IllegalArgumentException("Invalid CancerStage key: $key")
    }
}