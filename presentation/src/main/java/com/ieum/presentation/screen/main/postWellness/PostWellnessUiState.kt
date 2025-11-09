package com.ieum.presentation.screen.main.postWellness

import com.ieum.presentation.model.post.DietUiModel

sealed class PostWellnessUiState {
    data object Idle : PostWellnessUiState()

    data object Loading : PostWellnessUiState()

    data class ShowUnusualSymptomsSheet(val callback: (String) -> Unit) : PostWellnessUiState()

    data class ShowMedicationTakenDialog(val callback: (Boolean) -> Unit) : PostWellnessUiState()

    data class ShowDietSheet(val callback: (DietUiModel) -> Unit) : PostWellnessUiState()

    data class ShowMemoSheet(val callback: (String) -> Unit) : PostWellnessUiState()
}