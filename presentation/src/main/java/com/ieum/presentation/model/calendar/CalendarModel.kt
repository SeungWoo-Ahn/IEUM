package com.ieum.presentation.model.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.CalendarLocale
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.TimeZone

abstract class CalendarModel {
    abstract val today: CalendarDate

    abstract val firstDayOfWeek: Int

    val yearRange: IntRange get() = 2025..today.year

    val currentMonth: CalendarMonth get() = getMonth(today.year, today.month)

    abstract fun getMonth(year: Int, month: Int): CalendarMonth

    abstract fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth
}

internal const val DaysInWeek: Int = 7

@RequiresApi(Build.VERSION_CODES.O)
internal class CalendarModelImpl(locale: CalendarLocale) : CalendarModel() {
    override val today: CalendarDate
        get() {
            val localDate = LocalDate.now()
            return CalendarDate(
                year = localDate.year,
                month = localDate.monthValue,
                dayOfMonth = localDate.dayOfMonth
            )
        }

    override val firstDayOfWeek: Int = WeekFields.of(locale).firstDayOfWeek.value

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        val firstDayLocalDate = getFirstDayLocalDate(year, month)
        return getMonth(firstDayLocalDate)
    }

    override fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth {
        if (addedMonthsCount <= 0) return from
        val firstDayLocalDate = getFirstDayLocalDate(from.year, from.month)
        val laterMonth = firstDayLocalDate.plusMonths(addedMonthsCount.toLong())
        return getMonth(laterMonth)
    }

    private fun getMonth(firstDayLocalDate: LocalDate): CalendarMonth {
        val difference = firstDayLocalDate.dayOfWeek.value - firstDayOfWeek
        val daysFromStartOfWeekToFirstOfMonth =
            if (difference < 0) {
                difference + DaysInWeek
            } else {
                difference
            }
        return CalendarMonth(
            year = firstDayLocalDate.year,
            month = firstDayLocalDate.monthValue,
            numberOfDays = firstDayLocalDate.lengthOfMonth(),
            daysFromStartOfWeekToFirstOfMonth = daysFromStartOfWeekToFirstOfMonth
        )
    }

    private fun getFirstDayLocalDate(year: Int, month: Int): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}

internal class LegacyCalendarModelImpl(locale: CalendarLocale) : CalendarModel() {
    override val today: CalendarDate
        get() {
            val systemCalendar = Calendar.getInstance()
            return CalendarDate(
                year = systemCalendar[Calendar.YEAR],
                month = systemCalendar[Calendar.MONTH] + 1,
                dayOfMonth = systemCalendar[Calendar.DAY_OF_MONTH]
            )
        }

    override val firstDayOfWeek: Int = dayInISO8601(Calendar.getInstance(locale).firstDayOfWeek)

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        val firstDayCalendar = getFirstDayCalendar(year, month)
        return getMonth(firstDayCalendar)
    }

    override fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth {
        if (addedMonthsCount <= 0) return from
        val laterMonth = getFirstDayCalendar(from.year, from.month)
        laterMonth.add(Calendar.MONTH, addedMonthsCount)
        return getMonth(laterMonth)
    }

    private fun dayInISO8601(day: Int): Int {
        val shiftedDay = (day + 6) % 7
        return if (shiftedDay == 0) 7 else shiftedDay
    }

    private fun getFirstDayCalendar(year: Int, month: Int): Calendar {
        val calendar = Calendar.getInstance(utcTimeZone)
        calendar.clear()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.DAY_OF_MONTH] = 1
        return calendar
    }

    private fun getMonth(firstDayCalendar: Calendar): CalendarMonth {
        val difference = dayInISO8601(firstDayCalendar[Calendar.DAY_OF_WEEK]) - firstDayOfWeek
        val daysFromStartOfWeekToFirstOfMonth =
            if (difference < 0) {
                difference + DaysInWeek
            } else {
                difference
            }
        return CalendarMonth(
            year = firstDayCalendar[Calendar.YEAR],
            month = firstDayCalendar[Calendar.MONTH] + 1,
            numberOfDays = firstDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH),
            daysFromStartOfWeekToFirstOfMonth = daysFromStartOfWeekToFirstOfMonth,
        )
    }

    companion object {
        internal val utcTimeZone = TimeZone.getTimeZone("UTC")
    }
}

fun createCalendarModel(locale: CalendarLocale): CalendarModel {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CalendarModelImpl(locale)
    } else {
        LegacyCalendarModelImpl(locale)
    }
}