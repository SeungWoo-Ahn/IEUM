package com.ieum.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.bottomsheet.IEUMBottomSheet
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.MultiLineTextField
import com.ieum.design_system.textfield.TextFieldState
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R
import com.ieum.presentation.model.post.DietaryStatusInfo
import com.ieum.presentation.model.post.DietaryStatusUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PostSheetQuestion(
    question: String,
) {
    Text(
        text = question,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
private fun PostSheetButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    DarkButton(
        text = stringResource(R.string.complete),
        enabled = enabled,
        roundedCorner = false,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificSymptomsSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    data: String,
    callback: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val textFieldState = remember { TextFieldState() }

    LaunchedEffect(Unit) {
        textFieldState.typeText(data)
    }

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_specific_symptoms),
            )
            MultiLineTextField(
                modifier = Modifier.height(140.dp),
                state = textFieldState,
                placeHolder = stringResource(R.string.placeholder_specific_symptoms),
            )
        }
        PostSheetButton {
            scope
                .launch {
                    callback(textFieldState.getTrimmedText())
                    sheetState.hide()
                }
                .invokeOnCompletion {
                    onDismissRequest()
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietaryStatusSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    data: DietaryStatusUiModel?,
    callback: (DietaryStatusUiModel) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val selectorState = remember { SingleSelectorState(DietaryStatusInfo.entries) }
    val textFieldState = remember { TextFieldState() }
    val buttonEnabled by remember { derivedStateOf { selectorState.validate() } }

    LaunchedEffect(Unit) {
        data?.let {
            selectorState.setItem(data.info)
            textFieldState.typeText(data.content)
        }
    }

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_dietary_status),
            )
            IEUMSpacer(size = 24)
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                selectorState.itemList.forEach { info ->
                    DietaryStatusInfoSelector(
                        modifier = Modifier.weight(1f),
                        info = info,
                        isSelected = selectorState.isSelected(info),
                        onClick = { selectorState.selectItem(info) },
                    )
                }
            }
            IEUMSpacer(size = 12)
            MultiLineTextField(
                modifier = Modifier.height(190.dp),
                state = textFieldState,
                placeHolder = stringResource(R.string.placeholder_dietary_status),
            )
        }
        PostSheetButton(enabled = buttonEnabled) {
            scope
                .launch {
                    callback(
                        DietaryStatusUiModel(
                            info = selectorState.selectedItem!!,
                            content = textFieldState.getTrimmedText()
                        )
                    )
                    sheetState.hide()
                }
                .invokeOnCompletion {
                    onDismissRequest()
                }
        }
    }
}

@Composable
private fun DietaryStatusInfoSelector(
    modifier: Modifier = Modifier,
    info: DietaryStatusInfo,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isSelected) Lime500 else Slate200,
                shape = MaterialTheme.shapes.medium,
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        info.icon()
        Text(
            text = stringResource(info.description),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    data: String,
    callback: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val textFieldState = remember { TextFieldState() }

    LaunchedEffect(Unit) {
        textFieldState.typeText(data)
    }

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_memo),
            )
            MultiLineTextField(
                modifier = Modifier.height(140.dp),
                state = textFieldState,
                placeHolder = stringResource(R.string.placeholder_memo),
            )
        }
        PostSheetButton {
            scope
                .launch {
                    callback(textFieldState.getTrimmedText())
                    sheetState.hide()
                }
                .invokeOnCompletion {
                    onDismissRequest()
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancerStageSheet(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    data: CancerDiagnoseUiModel,
    callback: (CancerStageUiModel) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val selectorState = remember { SingleSelectorState(CancerStageUiModel.entries) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        selectorState.setItem(data.stage)
    }

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            Text(
                text = stringResource(R.string.guide_select_cancer_stage, stringResource(data.key.displayName)),
                style = MaterialTheme.typography.headlineLarge,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                selectorState.itemList.forEach { cancerStage ->
                    RegisterSelector(
                        isSelected = selectorState.isSelected(cancerStage),
                        name = stringResource(cancerStage.description),
                        onClick = {
                            scope
                                .launch {
                                    callback(cancerStage)
                                    sheetState.hide()
                                }
                                .invokeOnCompletion {
                                    onDismissRequest()
                                }
                        }
                    )
                }
            }
        }
    }
}