package com.ieum.presentation.screen.component

import androidx.compose.runtime.Composable
import com.ieum.presentation.state.CancerStageSheetState
import com.ieum.presentation.state.CancerStageSheetUiState

@Composable
internal fun CancerStageComponent(
    sheetState: CancerStageSheetState,
) {

    if (sheetState.uiState is CancerStageSheetUiState.Show) {
        // TODO: CancerStage Bottom Sheet
    }
}