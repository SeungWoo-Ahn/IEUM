package com.ieum.design_system.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface ISelectorState<T> {
    val itemList: List<T>

    fun selectItem(item: T)

    fun isSelected(item: T): Boolean

    fun validate(): Boolean
}

interface ISingleSelectorState<T> : ISelectorState<T> {
    val selectedItem: T?
}

interface IMultipleSelectorState<T> : ISelectorState<T> {
    val selectedItemList: List<T>
}

class SingleSelectorState<T>(
    override val itemList: List<T>
) : ISingleSelectorState<T> {
    private var _selectedItem by mutableStateOf<T?>(null)

    override val selectedItem: T? get() = _selectedItem

    override fun selectItem(item: T) {
        _selectedItem = if (isSelected(item)) {
            null
        } else {
            item
        }
    }

    fun setItem(item: T) {
        _selectedItem = item
    }

    override fun isSelected(item: T): Boolean {
        return item == selectedItem
    }

    override fun validate(): Boolean {
        return selectedItem != null
    }
}

class MultipleSelectorState<T>(
    override val itemList: List<T>
) : IMultipleSelectorState<T> {
    private var _selectedItemList = mutableStateListOf<T>()

    override val selectedItemList: List<T> get() = _selectedItemList

    override fun selectItem(item: T) {
        if (isSelected(item)) {
            _selectedItemList.remove(item)
        } else {
            _selectedItemList.add(item)
        }
    }

    override fun isSelected(item: T): Boolean {
        return item in selectedItemList
    }

    override fun validate(): Boolean {
        return selectedItemList.isNotEmpty()
    }
}