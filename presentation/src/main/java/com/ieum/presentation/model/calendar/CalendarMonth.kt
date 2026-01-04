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

    fun getDateRange(): Pair<String, String> {
        fun getDate(year: Int, month: Int, day: Int): String {
            return "%d-%02d-%02d".format(year, month, day)
        }
        return getDate(year, month, 1) to getDate(year, month, numberOfDays)
    }

    override fun toString(): String {
        return "%d.%02d".format(year, month)
    }
}
