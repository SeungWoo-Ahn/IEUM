package com.ieum.presentation.state

sealed class DatePickerState {
    data object Idle : DatePickerState()

    data class Show(
        val date: String,
        val onDateSelected: (String) -> Unit,
        val onDismissRequest: () -> Unit,
    ) : DatePickerState()
}
