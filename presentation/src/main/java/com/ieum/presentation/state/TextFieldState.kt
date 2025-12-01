package com.ieum.presentation.state

import com.ieum.design_system.textfield.MaxLengthTextFieldState

class CycleState : MaxLengthTextFieldState(maxLength = 2) {
    override fun typeText(text: String) {
        if (text.isEmpty()) {
            super.typeText(text)
            return
        }
        val cycle = text.toIntOrNull() ?: return
        if (cycle > 0) {
            super.typeText(cycle.toString())
        }
    }
}