package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.dialog.IEUMDialog
import com.ieum.design_system.icon.CompleteIcon
import com.ieum.design_system.icon.IncompleteIcon
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R

@Composable
fun TakingMedicineDialog(
    data: Boolean?,
    callback: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
) {
    IEUMDialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .background(color = White)
                .padding(all = screenPadding)
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_taking_medicine)
            )
            IEUMSpacer(size = 32)
            TakingMedicineSelector(
                name = stringResource(R.string.complete),
                icon = { CompleteIcon() },
                isSelected = data == true,
                onClick = {
                    callback(true)
                    onDismissRequest()
                }
            )
            IEUMSpacer(size = 12)
            TakingMedicineSelector(
                name = stringResource(R.string.incomplete),
                icon = { IncompleteIcon() },
                isSelected = data == false,
                onClick = {
                    callback(false)
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
private fun TakingMedicineSelector(
    name: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSelected) Lime500 else Slate200,
                shape = MaterialTheme.shapes.medium
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}