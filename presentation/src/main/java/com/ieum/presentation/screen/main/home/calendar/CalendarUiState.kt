package com.ieum.presentation.screen.main.home.calendar

import com.ieum.presentation.mapper.calcAverage
import com.ieum.presentation.model.calendar.CalendarFilter
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.CalendarWellnessUiModel
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.MoodUiModel

data class CalendarUiState(
    val displayedMonth: CalendarMonth,
    val selectedFilter: CalendarFilter,
    private val wellnessList: List<CalendarWellnessUiModel>,
) {
    val dateUiStateByDayOfMonth: Map<Int, CalendarDateUiState> =
        wellnessList
            .groupBy { it.dayOfMonth }
            .mapValues { (_, list) ->
                CalendarDateUiState(wellnessList = list)
            }

    val monthSummaryUiState: CalendarMonthSummaryUiState =
        CalendarMonthSummaryUiState(
            dayOfMonth = displayedMonth.numberOfDays,
            postDayCount = dateUiStateByDayOfMonth.size,
            averageMood = wellnessList.map { it.mood }.calcAverage()
        )

    companion object {
        fun getIdleState(currentMonth: CalendarMonth): CalendarUiState =
            CalendarUiState(
                displayedMonth = currentMonth,
                selectedFilter = CalendarFilter.WELLNESS,
                wellnessList = emptyList(),
            )
    }
}

data class CalendarDateUiState(
    private val wellnessList: List<CalendarWellnessUiModel>,
) {
    val averageMood: MoodUiModel get() =
        wellnessList.map { it.mood }.calcAverage()

    val unusualSymptomsExist: Boolean get() =
        wellnessList.any { it.unusualSymptomsExist }

    val averageAmountEaten: AmountEatenUiModel? get() =
        wellnessList.mapNotNull { it.amountEaten }.calcAverage()
}

data class CalendarMonthSummaryUiState(
    val dayOfMonth: Int,
    val postDayCount: Int,
    val averageMood: MoodUiModel,
) {
    val percentage: String get() {
        val percent = (postDayCount.toFloat() / dayOfMonth) * 100
        return "%.1f".format(percent)
    }
}