package com.ieum.presentation.screen.main.postTreatmentRecords

import com.ieum.presentation.model.post.DietaryStatusUiModel

sealed class PostTreatmentRecordsUiState {
    data object Idle : PostTreatmentRecordsUiState()

    data object Loading : PostTreatmentRecordsUiState()

    data class ShowSpecificSymptomsSheet(
        val data: String,
        val callback: (String) -> Unit,
    ) : PostTreatmentRecordsUiState()

    data class ShowTakingMedicineDialog(
        val data: Boolean?,
        val callback: (Boolean) -> Unit,
    ) : PostTreatmentRecordsUiState()

    data class ShowDietaryStatusSheet(
        val data: DietaryStatusUiModel?,
        val callback: (DietaryStatusUiModel) -> Unit,
    ) : PostTreatmentRecordsUiState()

    data class ShowMemoSheet(
        val data: String,
        val callback: (String) -> Unit,
    ) : PostTreatmentRecordsUiState()
}