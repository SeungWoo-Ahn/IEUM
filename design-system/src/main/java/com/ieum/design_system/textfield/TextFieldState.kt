package com.ieum.design_system.textfield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface ITextFieldState {
    val typedText: String

    fun typeText(text: String)

    fun getTrimmedText(): String

    fun validate(): Boolean
}

interface IMaxLengthTextFieldState : ITextFieldState {
    val maxLength: Int

    fun resetText()
}

class TextFieldState : ITextFieldState {
    private var _typedText by mutableStateOf("")
    override val typedText: String get() = _typedText

    override fun typeText(text: String) {
        _typedText = text
    }

    override fun getTrimmedText(): String {
        return typedText.trim()
    }

    override fun validate(): Boolean {
        return getTrimmedText().isNotEmpty()
    }
}

open class MaxLengthTextFieldState(
    override val maxLength: Int
) : IMaxLengthTextFieldState {
    private var _typedText by mutableStateOf("")
    override val typedText: String get() = _typedText

    override fun typeText(text: String) {
        if (text.length <= maxLength) {
            _typedText = text
        }
    }

    override fun getTrimmedText(): String {
        return typedText.trim()
    }

    override fun resetText() {
        _typedText = ""
    }

    override fun validate(): Boolean {
        return getTrimmedText().length in 1..maxLength
    }
}