package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ieum.design_system.icon.InfoCircleIcon
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.design_system.theme.Gray600
import com.ieum.presentation.R

@Composable
fun TypeNickname(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InfoCircleIcon()
            IEUMSpacer(size = 2)
            Text(
                text = stringResource(R.string.info_nickname),
                style = MaterialTheme.typography.bodyMedium,
                color = Gray600,
            )
        }
        IEUMSpacer(size = 44)
        MaxLengthTextField(
            state = state,
            placeHolder = stringResource(R.string.placeholder_type_nickname)
        )
    }
}

@Composable
fun TypeInterest(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MaxLengthTextField(
            state = state,
            placeHolder = stringResource(R.string.placeholder_type_interest)
        )
    }
}