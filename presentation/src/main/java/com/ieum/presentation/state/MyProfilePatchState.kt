package com.ieum.presentation.state

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ieum.design_system.textfield.MaxLengthTextFieldState
import com.ieum.domain.model.user.Chemotherapy
import com.ieum.domain.model.user.ProfileProperty
import com.ieum.domain.model.user.RadiationTherapy
import com.ieum.domain.model.user.Surgery
import com.ieum.presentation.mapper.DateFormatStrategy
import com.ieum.presentation.mapper.formatDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SurgeryItemState(initData: Surgery? = null) {
    var date by mutableStateOf(
        initData?.date ?: formatDate(DateFormatStrategy.Today)
    )
        private set

    val descriptionState = MaxLengthTextFieldState(20)
        .apply { initData?.description?.let { typeText(it) } }

    fun dateCallback(date: String) {
        this.date = date
    }

    fun validate(): Boolean = descriptionState.validate()

    fun getSurgery(): Surgery =
        Surgery(
            date = date,
            description = descriptionState.getTrimmedText(),
        )
}

class SurgeryState(
    initData: ProfileProperty<List<Surgery>>,
) {
    private val _stateList = mutableStateListOf<SurgeryItemState>()
    val stateList: List<SurgeryItemState> get() = _stateList

    var isOpened by mutableStateOf(initData.open)
        private set

    var pickerState by mutableStateOf<DatePickerState>(DatePickerState.Idle)
        private set

    init {
        initData.data?.let {
            _stateList += it.map { therapy -> SurgeryItemState(therapy) }
        } ?: run {
            addItemState()
        }
    }

    fun addItemState() {
        _stateList += SurgeryItemState()
    }

    fun removeItemState(itemState: SurgeryItemState) {
        _stateList -= itemState
    }

    private fun dismissDatePicker() {
        pickerState = DatePickerState.Idle
    }

    fun showDatePicker(itemState: SurgeryItemState) {
        pickerState = DatePickerState.Show(
            date = itemState.date,
            onDateSelected = itemState::dateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun toggleIsOpened() {
        isOpened = isOpened.not()
    }

    fun validate(): Boolean = stateList.all(SurgeryItemState::validate)

    fun getProfileProperty(): ProfileProperty<List<Surgery>> =
        ProfileProperty(
            data = stateList.map(SurgeryItemState::getSurgery),
            open = isOpened,
        )
}

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

class ChemoTherapyItemState(initData: Chemotherapy? = null) {
    val cycleState = CycleState()
        .apply { initData?.cycle?.let { typeText(it.toString()) } }

    var startDate by mutableStateOf(
        initData?.startDate ?: formatDate(DateFormatStrategy.Today)
    )
        private set
    var endDate by mutableStateOf(
        initData?.endDate
            ?: initData?.startDate
            ?: formatDate(DateFormatStrategy.Today)
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

    fun validate(): Boolean = cycleState.validate()

    fun getChemotherapy(): Chemotherapy =
        Chemotherapy(
            cycle = cycleState.getTrimmedText().toInt(),
            startDate = startDate,
            endDate = if (isOngoing) null else endDate,
        )
}

class ChemotherapyState(
    initData: ProfileProperty<List<Chemotherapy>>,
) {
    private val _stateList = mutableStateListOf<ChemoTherapyItemState>()
    val stateList: List<ChemoTherapyItemState> get() = _stateList

    var isOpened by mutableStateOf(initData.open)
        private set

    var pickerState by mutableStateOf<DatePickerState>(DatePickerState.Idle)
        private set

    init {
        initData.data?.let {
            _stateList += it.map { therapy -> ChemoTherapyItemState(therapy) }
        } ?: run {
            addItemState()
        }
    }

    fun addItemState() {
        _stateList += ChemoTherapyItemState()
    }

    fun removeItemState(itemState: ChemoTherapyItemState) {
        _stateList -= itemState
    }

    private fun dismissDatePicker() {
        pickerState = DatePickerState.Idle
    }

    fun showStartDatePicker(itemState: ChemoTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = itemState.startDate,
            onDateSelected = itemState::startDateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun showEndDatePicker(itemState: ChemoTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = itemState.endDate,
            onDateSelected = itemState::endDateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun toggleIsOpened() {
        isOpened = isOpened.not()
    }

    fun validate(): Boolean = stateList.all(ChemoTherapyItemState::validate)

    fun getProfileProperty(): ProfileProperty<List<Chemotherapy>> =
        ProfileProperty(
            data = stateList.map(ChemoTherapyItemState::getChemotherapy),
            open = isOpened,
        )
}

class RadiationTherapyItemState(initData: RadiationTherapy? = null) {
    var startDate by mutableStateOf(
        initData?.startDate ?: formatDate(DateFormatStrategy.Today)
    )
        private set
    var endDate by mutableStateOf(
        initData?.endDate
            ?: initData?.startDate
            ?: formatDate(DateFormatStrategy.Today)
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

    fun removeItemState(itemState: RadiationTherapyItemState) {
        _stateList -= itemState
    }

    private fun dismissDatePicker() {
        pickerState = DatePickerState.Idle
    }

    fun showStartDatePicker(itemState: RadiationTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = itemState.startDate,
            onDateSelected = itemState::startDateCallback,
            onDismissRequest = ::dismissDatePicker,
        )
    }

    fun showEndDatePicker(itemState: RadiationTherapyItemState) {
        pickerState = DatePickerState.Show(
            date = itemState.endDate,
            onDateSelected = itemState::endDateCallback,
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