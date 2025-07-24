package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.presentation.mapper.toDescription
import com.ieum.presentation.state.DiagnoseState
import kotlinx.coroutines.CoroutineScope

@Composable
fun DiagnoseComponent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    state: DiagnoseState,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        state.cancerDiagnoseState.diagnoseList.forEach { diagnose ->
            RegisterSelector(
                isSelected = state.cancerDiagnoseState.isSelected(diagnose),
                name = stringResource(diagnose.key.displayName) +
                        if (diagnose.stage != null) {
                            stringResource(diagnose.stage.toDescription())
                        }
                        else {
                            ""
                        },
                onClick = { state.cancerDiagnoseState.onDiagnose(diagnose) }
            )
        }
        state.commonDiagnoseState.itemList.forEach { diagnose ->
            RegisterSelector(
                isSelected = state.commonDiagnoseState.isSelected(diagnose),
                name = stringResource(diagnose.displayName),
                onClick = { state.commonDiagnoseState.selectItem(diagnose) }
            )
        }
    }
    CancerStageComponent(
        scope = scope,
        sheetState = state.cancerDiagnoseState.cancerStageSheetState,
    )
}