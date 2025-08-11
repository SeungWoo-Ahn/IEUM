package com.ieum.presentation.screen.main.postTreatmentRecords

import com.ieum.presentation.model.post.DietaryStatusUiModel

sealed class PostTreatmentRecordsUiState {
    data object Idle : PostTreatmentRecordsUiState()

    data object Loading : PostTreatmentRecordsUiState()

    data class ShowSpecificSymptomsSheet(val callback: (String) -> Unit) : PostTreatmentRecordsUiState()

    data class ShowTakingMedicineDialog(val callback: (Boolean) -> Unit) : PostTreatmentRecordsUiState()

    data class ShowDietaryStatusSheet(val callback: (DietaryStatusUiModel) -> Unit) : PostTreatmentRecordsUiState()

    data class ShowMemoSheet(val callback: (String) -> Unit) : PostTreatmentRecordsUiState()
}