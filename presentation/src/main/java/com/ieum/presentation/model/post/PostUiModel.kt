package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ieum.design_system.icon.EatBarelyIcon
import com.ieum.design_system.icon.EatSmallAmountsIcon
import com.ieum.design_system.icon.EatWellIcon
import com.ieum.design_system.icon.MoodBadIcon
import com.ieum.design_system.icon.MoodGoodIcon
import com.ieum.design_system.icon.MoodHappyIcon
import com.ieum.design_system.icon.MoodNormalIcon
import com.ieum.design_system.icon.MoodWorstIcon
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

enum class MoodUiModel(
    @StringRes val description: Int,
    val strokeColor: Color,
    val backGroundColor: Color,
    val icon: @Composable () -> Unit,
) {
    HAPPY(
        description = R.string.mood_happy,
        strokeColor = Color(0x80F0B100),
        backGroundColor = Color(0x33FDC700),
        icon = { MoodHappyIcon() }
    ),
    GOOD(
        description = R.string.mood_good,
        strokeColor = Color(0x809AE600),
        backGroundColor = Color(0x339AE600),
        icon = { MoodGoodIcon() }
    ),
    NORMAL(
        description = R.string.mood_normal,
        strokeColor = Color(0x8000BBA7),
        backGroundColor = Color(0x3300D5BE),
        icon = { MoodNormalIcon() }
    ),
    BAD(
        description = R.string.mood_happy,
        strokeColor = Color(0x8000D3F3),
        backGroundColor = Color(0x3300D3F3),
        icon = { MoodBadIcon() }
    ),
    WORST(
        description = R.string.mood_happy,
        strokeColor = Color(0x8051A2FF),
        backGroundColor = Color(0x3351A2FF),
        icon = { MoodWorstIcon() }
    ),
}

data class PostWellnessUiModel(
    val mood: MoodUiModel?,
    val unusualSymptoms: String,
    val medicationTaken: Boolean?,
    val diet: DietUiModel?,
    val memo: String,
    val imageList: List<ImageSource>,
    val shared: Boolean,
) {
    fun validate(): Boolean = mood != null

    companion object {
        val EMPTY = PostWellnessUiModel(
            mood = null,
            unusualSymptoms = "",
            medicationTaken = null,
            diet = null,
            memo = "",
            imageList = emptyList(),
            shared = false,
        )
    }
}