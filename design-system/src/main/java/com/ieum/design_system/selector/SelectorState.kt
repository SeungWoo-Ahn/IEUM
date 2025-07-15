package com.ieum.design_system.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface ISelectorState<T> {
    val itemList: List<T>

    fun selectItem(item: T)

    fun isSelected(item: T): Boolean
}

interface ISingleSelectorState<T> : ISelectorState<T> {
    val selectedItem: T?
}

class SingleSelectorState<T>(
    override val itemList: List<T>
) : ISingleSelectorState<T> {
    private var _selectedItem by mutableStateOf<T?>(null)

    override val selectedItem: T? get() = _selectedItem

    override fun selectItem(item: T) {
        _selectedItem = if (isSelected(item)) null else item
    }

    override fun isSelected(item: T): Boolean {
        return item == selectedItem
    }
}