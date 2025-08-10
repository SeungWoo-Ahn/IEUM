package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.EatNothingIcon
import com.ieum.design_system.icon.EatSmallAmountsIcon
import com.ieum.design_system.icon.EatWellIcon
import com.ieum.presentation.R

enum class DietaryStatusInfo(
    @StringRes val description: Int,
    val icon: @Composable () -> Unit,
) {
    EAT_WELL(R.string.eat_well, { EatWellIcon() }),
    EAT_SMALL_AMOUNTS(R.string.eat_small_amounts, { EatSmallAmountsIcon() }),
    EAT_NOTHING(R.string.eat_nothing, { EatNothingIcon() }),
}

data class DietaryStatusUiModel(
    val info: DietaryStatusInfo,
    val content: String,
)