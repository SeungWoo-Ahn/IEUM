package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.EatBarelyIcon
import com.ieum.design_system.icon.EatSmallAmountsIcon
import com.ieum.design_system.icon.EatWellIcon
import com.ieum.presentation.R

enum class AmountEatenUiModel(
    @StringRes val description: Int,
    val icon: @Composable () -> Unit,
) {
    WELL(R.string.eat_well, { EatWellIcon() }),
    SMALL(R.string.eat_small_amounts, { EatSmallAmountsIcon() }),
    BARELY(R.string.eat_barely, { EatBarelyIcon() }),
}