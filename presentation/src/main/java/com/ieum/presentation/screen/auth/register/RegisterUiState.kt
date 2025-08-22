package com.ieum.presentation.screen.auth.register

import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel

sealed class RegisterUiState {
    data object Idle : RegisterUiState()

    data class ShowCancerStageSheet(
        val data: CancerDiagnoseUiModel,
        val callback: (CancerStageUiModel) -> Unit
    ) : RegisterUiState()

    data object Loading : RegisterUiState()
}