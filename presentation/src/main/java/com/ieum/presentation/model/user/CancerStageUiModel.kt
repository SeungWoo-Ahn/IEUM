package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class CancerStageUiModel(@StringRes val description: Int) {
    STAGE_1(description = R.string.cancer_stage_1),
    STAGE_2(description = R.string.cancer_stage_2),
    STAGE_3(description = R.string.cancer_stage_3),
    STAGE_4(description = R.string.cancer_stage_4),
    STAGE_UNKNOWN(description = R.string.cancer_stage_unknown),
}