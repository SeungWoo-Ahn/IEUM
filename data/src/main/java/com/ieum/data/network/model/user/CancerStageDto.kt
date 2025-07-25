package com.ieum.data.network.model.user

enum class CancerStageDto(val key: String) {
    STAGE_1("stage1"),
    STAGE_2("stage2"),
    STAGE_3("stage3"),
    STAGE_4("stage4");

    companion object {
        private val map = entries.associateBy(CancerStageDto::key)

        fun fromKey(key: String): CancerStageDto = map[key]
            ?: throw IllegalArgumentException("Invalid CancerStageDto key: $key")
    }
}