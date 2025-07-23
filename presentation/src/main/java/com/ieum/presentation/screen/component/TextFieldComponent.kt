package com.ieum.presentation.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.presentation.R

@Composable
fun TypeNickname(
    state: IMaxLengthTextFieldState,
) {
    MaxLengthTextField(
        state = state,
        placeHolder = stringResource(R.string.placeholder_type_nickname)
    )
}

@Composable
fun TypeInterest(
    state: IMaxLengthTextFieldState,
) {
    MaxLengthTextField(
        state = state,
        placeHolder = stringResource(R.string.placeholder_type_interest)
    )
}