package com.ieum.presentation.state

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ieum.domain.model.user.ProfileProperty
import com.ieum.domain.model.user.RadiationTherapy
import com.ieum.presentation.mapper.DateFormatStrategy
import com.ieum.presentation.mapper.formatDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private fun compareDateStrings(dateString1: String, dateString2: String): Int {
    val pattern = "yyyy-MM-dd"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val date1 = LocalDate.parse(dateString1, formatter)
        val date2 = LocalDate.parse(dateString2, formatter)
        date1.compareTo(date2)
    } else {
        val formatter = SimpleDateFormat(pattern, Locale.KOREA)
        val date1 = formatter.parse(dateString1) as Date
        val date2 = formatter.parse(dateString2) as Date
        date1.compareTo(date2)
    }
}

private fun String.isBefore(date: String): Boolean {
    return compareDateStrings(this, date) < 0
}

private fun String.isAfter(date: String): Boolean {
    return compareDateStrings(this, date) > 0
}

class RadiationTherapyItemState(initData: RadiationTherapy? = null) {
    var startDate by mutableStateOf(
        initData?.startDate ?: formatDate(DateFormatStrategy.Today)
    )
        private set
    var endDate by mutableStateOf(
        initData?.endDate ?: formatDate(DateFormatStrategy.Today)
    )
        private set
    var isOngoing by mutableStateOf(
        if (initData == null) false else initData.endDate == null
    )
        private set

    fun startDateCallback(date: String) {
        if (date.isAfter(endDate)) {
            endDate = date
        }
        startDate = date
    }

    fun endDateCallback(date: String) {
        if (date.isBefore(startDate)) {
            startDate = date
        }
        endDate = date
    }

    fun toggleIsOngoing() {
        isOngoing = isOngoing.not()
    }

    fun getRadiationTherapy(): RadiationTherapy =
        RadiationTherapy(
            startDate = startDate,
            endDate = if (isOngoing) null else endDate,
        )
}

class RadiationTherapyState(
    initData: ProfileProperty<List<RadiationTherapy>>,
) {
    private val _stateList = mutableStateListOf<RadiationTherapyItemState>()
    val stateList: List<RadiationTherapyItemState> get() = _stateList

    var isOpened by mutableStateOf(initData.open)
        private set

    var pickerState by mutableStateOf<DatePickerState>(DatePickerState.Idle)
        private set

    init {
        initData.data?.let {
            _stateList += it.map { therapy -> RadiationTherapyItemState(therapy) }
        } ?: run {
            addItemState()
        }
    }

    fun addItemState() {
        _stateList += RadiationTherapyItemState()
    }

    fun removeItemState(state: RadiationTherapyItemState) {
        _stateList -= state
    }

    private fun dismissDatePicker() {
        pickerState = DatePickerState.Idle
    }

    fun showStartDatePicker(state: RadiationTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = state.startDate,
            onDateSelected = state::startDateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun showEndDatePicker(state: RadiationTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = state.endDate,
            onDateSelected = state::endDateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun toggleIsOpened() {
        isOpened = isOpened.not()
    }

    fun getProfileProperty(): ProfileProperty<List<RadiationTherapy>> =
        ProfileProperty(
            data = stateList.map(RadiationTherapyItemState::getRadiationTherapy).ifEmpty { null },
            open = isOpened,
        )
}