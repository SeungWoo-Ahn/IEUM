package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.bottomsheet.IEUMBottomSheet
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.textfield.MultiLineTextField
import com.ieum.design_system.textfield.TextFieldState
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun PostSheetQuestion(
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
    val textFieldState = remember { TextFieldState(data) }

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