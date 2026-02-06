package com.ieum.presentation.screen.auth.register

import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel

sealed class RegisterUiState {
    data object Idle : RegisterUiState()

    data object Loading : RegisterUiState()
}

sealed class RegisterSheetState {
    data object Idle : RegisterSheetState()

    data class ShowCancerStageSheet(
        val data: CancerDiagnoseUiModel,
        val callback: (CancerStageUiModel) -> Unit
    ) : RegisterSheetState()

    data object ShowPolicySheet : RegisterSheetState()
}