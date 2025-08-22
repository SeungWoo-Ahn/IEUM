package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.NextButton
import com.ieum.design_system.button.SkipOrNextButton
import com.ieum.design_system.icon.InfoCircleIcon
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.textfield.ITextFieldState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.design_system.textfield.StylelessTextField
import com.ieum.design_system.theme.Gray600
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.R

@Composable
fun TypeNickname(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    state: IMaxLengthTextFieldState,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = screenPadding)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(44.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InfoCircleIcon()
                Text(
                    text = stringResource(R.string.info_nickname),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray600,
                )
            }
            MaxLengthTextField(
                state = state,
                placeHolder = stringResource(R.string.placeholder_type_nickname)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            NextButton(
                enabled = buttonEnabled,
                onClick = onButtonClick
            )
        }
    }
}

@Composable
fun TypeInterest(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    state: IMaxLengthTextFieldState,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .padding(horizontal = screenPadding)
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            MaxLengthTextField(
                state = state,
                placeHolder = stringResource(R.string.placeholder_type_interest)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            SkipOrNextButton(
                enabled = buttonEnabled,
                onNext = onButtonClick,
            )
        }
    }
}

@Composable
fun TypeTitle(state: ITextFieldState) {
    StylelessTextField(
        modifier = Modifier
            .padding(
                horizontal = 18.dp,
                vertical = 16.dp,
            ),
        state = state,
        placeHolder = stringResource(R.string.placeholder_title),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        imeAction = ImeAction.Next,
    )
}

@Composable
fun TypeStory(state: ITextFieldState) {
    StylelessTextField(
        modifier = Modifier
            .height(394.dp)
            .padding(all = 18.dp),
        state = state,
        placeHolder = stringResource(R.string.placeholder_story),
        singleLine = false,
        textStyle = MaterialTheme.typography.bodySmall,
        imeAction = ImeAction.Unspecified,
    )
}