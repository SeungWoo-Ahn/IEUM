package com.ieum.design_system.textfield

interface ITextFieldState {
    fun typeText(text: String)

    fun getTrimmedText(): String

    fun validate(): Boolean
}

interface IMaxLengthTextFieldState {
    val maxLength: Int
}
