package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.SelectedCountButton
import com.ieum.design_system.selector.IMultipleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.presentation.mapper.toDescription
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.DiagnoseKey
import com.ieum.presentation.state.CancerDiagnoseState
import com.ieum.presentation.state.DiagnoseState

@Composable
fun DiagnoseComponent(
    modifier: Modifier = Modifier,
    nextEnabled: Boolean,
    diagnoseState: DiagnoseState,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        CancerDiagnoseSelectArea(
            state = diagnoseState.cancerDiagnoseState,
        )
        DiagnoseSelectArea(
            state = diagnoseState.commonDiagnoseState,
        )
        IEUMSpacer(modifier = Modifier.weight(1f))
        SelectedCountButton(
            enabled = nextEnabled,
            selectedCount = diagnoseState.getSelectedCnt(),
            onClick = onNextStep
        )
    }
    CancerStageComponent(
        sheetState = diagnoseState.cancerDiagnoseState.cancerStageSheetState,
    )
}

@Composable
private fun CancerDiagnoseSelectArea(
    modifier: Modifier = Modifier,
    state: CancerDiagnoseState,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        state.diagnoseList.forEach { diagnose ->
            CancerDiagnoseSelector(
                isSelected = state.isSelected(diagnose),
                diagnose = diagnose,
                onClick = { state.onDiagnose(diagnose) }
            )
            IEUMSpacer(size = 16)
        }
    }
}

@Composable
private fun CancerDiagnoseSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    diagnose: CancerDiagnoseUiModel,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                color = if (isSelected) {
                    Color.Black
                } else {
                    Color.White
                },
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(diagnose.key.displayName),
                color = if (isSelected) {
                    Color.White
                } else {
                    Color.Black
                }
            )
            diagnose.stage?.let {
                IEUMSpacer(size = 10)
                Text(
                    text = stringResource(it.toDescription()),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun DiagnoseSelectArea(
    modifier: Modifier = Modifier,
    state: IMultipleSelectorState<DiagnoseKey>,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        state.itemList.forEach { diagnose ->
            DiagnoseSelector(
                isSelected = state.isSelected(diagnose),
                diagnose = diagnose,
                onClick = { state.selectItem(diagnose) }
            )
            IEUMSpacer(size = 16)
        }
    }
}

@Composable
private fun DiagnoseSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    diagnose: DiagnoseKey,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                color = if (isSelected) {
                    Color.Black
                } else {
                    Color.White
                },
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(diagnose.displayName),
            color = if (isSelected) {
                Color.White
            } else {
                Color.Black
            }
        )
    }
}