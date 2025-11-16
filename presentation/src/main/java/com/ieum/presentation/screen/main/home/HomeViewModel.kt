package com.ieum.presentation.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ieum.presentation.screen.component.BottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var selectedBottomNavigationItem by mutableStateOf(BottomNavigationItem.Feed)
        private set

    fun onBottomNavigationItemClick(item: BottomNavigationItem) {
        selectedBottomNavigationItem = item
    }
}