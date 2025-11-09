package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.EatBarelyIcon
import com.ieum.design_system.icon.EatSmallAmountsIcon
import com.ieum.design_system.icon.EatWellIcon
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R

enum class AmountEatenUiModel(
    @StringRes val description: Int,
    val icon: @Composable () -> Unit,
) {
    WELL(R.string.eat_well, { EatWellIcon() }),
    SMALL(R.string.eat_small_amounts, { EatSmallAmountsIcon() }),
    BARELY(R.string.eat_barely, { EatBarelyIcon() }),
}

data class DietUiModel(
    val amountEaten: AmountEatenUiModel,
    val mealContent: String,
)

data class PostWellnessUiModel(
    val unusualSymptoms: String,
    val medicationTaken: Boolean?,
    val diet: DietUiModel?,
    val memo: String,
    val imageList: List<ImageSource>,
    val shared: Boolean,
) {
    fun validate(): Boolean = true // TODO: 기분 선택 추가 이후 수정 필요

    companion object {
        val EMPTY = PostWellnessUiModel(
            unusualSymptoms = "",
            medicationTaken = null,
            diet = null,
            memo = "",
            imageList = emptyList(),
            shared = false,
        )
    }
}