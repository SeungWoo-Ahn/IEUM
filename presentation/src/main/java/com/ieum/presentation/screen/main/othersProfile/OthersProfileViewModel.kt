package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OthersProfileViewModel @Inject constructor() : ViewModel() {

    var currentTab by mutableStateOf(OthersProfileTab.PROFILE)
        private set

    fun selectTab(tab: OthersProfileTab) {
        currentTab = tab
    }
}