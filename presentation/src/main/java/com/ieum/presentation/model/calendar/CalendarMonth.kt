package com.ieum.presentation.model.calendar

data class CalendarMonth(
    val year: Int,
    val month: Int,
    val numberOfDays: Int,
    val daysFromStartOfWeekToFirstOfMonth: Int,
) {
    fun indexIn(years: IntRange): Int {
        return (year - years.first) * 12 + month - 1
    }

    override fun toString(): String {
        return "$year.$month"
    }
}
