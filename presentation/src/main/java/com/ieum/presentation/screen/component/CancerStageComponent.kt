package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ieum.design_system.bottomsheet.IEUMBottomSheet
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.presentation.R
import com.ieum.presentation.mapper.toDescription
import com.ieum.presentation.state.CancerStageSheetState
import com.ieum.presentation.state.CancerStageSheetUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CancerStageComponent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    sheetState: CancerStageSheetState,
) {
    val uiState = sheetState.uiState
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (uiState is CancerStageSheetUiState.Show) {
        IEUMBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = sheetState::dismiss
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.guide_select_cancer_stage, stringResource(uiState.key.displayName)),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                IEUMSpacer(size = 32)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    sheetState.cancerStageState.itemList.forEach { cancerStage ->
                        RegisterSelector(
                            isSelected = sheetState.cancerStageState.isSelected(cancerStage),
                            name = stringResource(cancerStage.toDescription()),
                            onClick = {
                                scope
                                    .launch {
                                        uiState.callback(cancerStage)
                                        bottomSheetState.hide()
                                    }
                                    .invokeOnCompletion {
                                        sheetState.dismiss()
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}