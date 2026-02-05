package com.ieum.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.paging.compose.collectAsLazyPagingItems
import com.ieum.design_system.bottomsheet.IEUMBottomSheet
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.button.Lime400Button
import com.ieum.design_system.checkbox.IEUMCheckBox
import com.ieum.design_system.icon.RightIcon
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.MultiLineTextField
import com.ieum.design_system.textfield.TextFieldState
import com.ieum.design_system.theme.Gray500
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.DietUiModel
import com.ieum.presentation.model.post.ReportTypeUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import com.ieum.presentation.state.CommentBottomSheetState
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
fun UnusualSymptomsSheet(
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
                question = stringResource(R.string.question_unusual_symptoms),
            )
            MultiLineTextField(
                modifier = Modifier.height(140.dp),
                state = textFieldState,
                placeHolder = stringResource(R.string.placeholder_unusual_symptoms),
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
fun DietSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    data: DietUiModel?,
    callback: (DietUiModel) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val selectorState = remember { SingleSelectorState(AmountEatenUiModel.entries) }
    val textFieldState = remember { TextFieldState() }
    val buttonEnabled by remember { derivedStateOf { selectorState.validate() } }

    LaunchedEffect(Unit) {
        data?.let {
            selectorState.setItem(data.amountEaten)
            textFieldState.typeText(data.mealContent)
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
                question = stringResource(R.string.question_diet),
            )
            IEUMSpacer(size = 24)
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                selectorState.itemList.forEach { amountEaten ->
                    AmountEatenSelector(
                        modifier = Modifier.weight(1f),
                        amountEaten = amountEaten,
                        isSelected = selectorState.isSelected(amountEaten),
                        onClick = { selectorState.selectItem(amountEaten) },
                    )
                }
            }
            IEUMSpacer(size = 12)
            MultiLineTextField(
                modifier = Modifier.height(190.dp),
                state = textFieldState,
                placeHolder = stringResource(R.string.placeholder_diet),
            )
        }
        PostSheetButton(enabled = buttonEnabled) {
            scope
                .launch {
                    callback(
                        DietUiModel(
                            amountEaten = selectorState.selectedItem!!,
                            mealContent = textFieldState.getTrimmedText()
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
private fun AmountEatenSelector(
    modifier: Modifier = Modifier,
    amountEaten: AmountEatenUiModel,
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
        amountEaten.icon()
        Text(
            text = stringResource(amountEaten.description),
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
                text = stringResource(R.string.guide_select_cancer_stage, data.key.displayName),
                style = MaterialTheme.typography.headlineLarge,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                selectorState.itemList.forEach { cancerStage ->
                    UserSelector(
                        name = cancerStage.description,
                        isSelected = selectorState.isSelected(cancerStage),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentListSheet(
    modifier: Modifier = Modifier,
    state: CommentBottomSheetState.Show,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val commentList = state.commentList.collectAsLazyPagingItems()
    val commentPostEnabled by remember { derivedStateOf {
        state.isLoading.not() && state.typedCommentState.validate()
    } }

    IEUMBottomSheet(
        sheetState = sheetState,
        needDragHandle = true,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                CommentListArea(
                    commentList = commentList,
                    onMenu = state::onMenu
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
            ) {
                TypeCommentArea(
                    state = state.typedCommentState,
                    postEnabled = commentPostEnabled,
                    onPost = state::postComment,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPolicySheet(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    buttonEnabled: Boolean,
    onRegister: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var policyConfirmed by remember { mutableStateOf(false) }

    fun togglePolicyConfirmed() {
        policyConfirmed = policyConfirmed.not()
    }

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        val uriHandler = LocalUriHandler.current
        val url = stringResource(R.string.privacy_policy_url)

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.guide_register_policy),
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable(onClick = ::togglePolicyConfirmed),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IEUMCheckBox(
                        checked = policyConfirmed,
                        onCheckedChange = { togglePolicyConfirmed() }
                    )
                    Text(
                        text = stringResource(R.string.privacy_policy),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                IconButton(onClick = { uriHandler.openUri(url) }) {
                    RightIcon(color = Gray500)
                }
            }
            Lime400Button(
                text = stringResource(R.string.register_with_confirm),
                enabled = buttonEnabled,
                onClick = {
                    scope
                        .launch {
                            policyConfirmed = true
                            onRegister()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportSheet(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    reportEnabled: Boolean,
    onReport: (ReportTypeUiModel) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    IEUMBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = stringResource(R.string.report),
                style = MaterialTheme.typography.titleSmall,
            )
            Column {
                ReportTypeUiModel.entries.fastForEach { type ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 10.dp,
                                vertical = 16.dp
                            )
                            .noRippleClickable(enabled = reportEnabled) {
                                scope
                                    .launch {
                                        onReport(type)
                                        sheetState.hide()
                                    }
                                    .invokeOnCompletion {
                                        onDismissRequest()
                                    }
                            },
                        text = stringResource(type.displayName),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
