package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.EatNothingIcon
import com.ieum.design_system.icon.EatSmallAmountsIcon
import com.ieum.design_system.icon.EatWellIcon
import com.ieum.domain.model.image.ImageSource
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

data class PostTreatmentRecordsUiModel(
    val specificSymptoms: String,
    val takingMedicine: Boolean?,
    val dietaryStatus: DietaryStatusUiModel?,
    val memo: String,
    val imageList: List<ImageSource>,
    val shareCommunity: Boolean,
) {
    fun validate(): Boolean = true // TODO: 기분 선택 추가 이후 수정 필요

    companion object {
        val EMPTY = PostTreatmentRecordsUiModel(
            specificSymptoms = "",
            takingMedicine = null,
            dietaryStatus = null,
            memo = "",
            imageList = emptyList(),
            shareCommunity = false,
        )
    }
}