package com.ieum.presentation.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf<FeedUiState>(FeedUiState.Idle)
        private set

    private val _selectedFilter = MutableStateFlow(DiagnoseFilterUiModel.ENTIRE)
    val selectedFilter: StateFlow<DiagnoseFilterUiModel> get() = _selectedFilter

    fun showAddPostDialog() {
        uiState = FeedUiState.ShowAddPostDialog
    }

    fun resetUiState() {
        uiState = FeedUiState.Idle
    }

    fun onFilter(filter: DiagnoseFilterUiModel) {
        _selectedFilter.update { filter }
    }
}