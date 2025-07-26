package com.ieum.design_system.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.theme.Gray300
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White

@Composable
fun IEUMTextField(
    modifier: Modifier = Modifier,
    state: ITextFieldState,
    placeHolder: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = state.typedText,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        textStyle = MaterialTheme.typography.titleLarge,
        onValueChange = state::typeText,
    ) { innerTextField ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        Slate900
                    } else {
                        Gray200
                    },
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 24.dp
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (state.typedText.isEmpty()) {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.titleLarge,
                    color = Gray300,
                )
            } else {
                innerTextField()
            }
        }
    }
}

@Preview
@Composable
internal fun IEUMTextFieldPreview() {
    val state = TextFieldState()
    IEUMTextField(
        state = state,
        placeHolder = "아이디",
    )
}

@Composable
fun MaxLengthTextField(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
    placeHolder: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        IEUMTextField(
            state = state,
            placeHolder = placeHolder,
            maxLines = maxLines,
            keyboardType = keyboardType,
            imeAction = imeAction,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "${state.typedText.length}",
                style = MaterialTheme.typography.bodyMedium,
                color = Lime500,
            )
            Text(
                text = "/${state.maxLength}",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray300,
            )
        }
    }
}

@Preview
@Composable
internal fun MaxLengthTextFieldPreview() {
    val state = MaxLengthTextFieldState(
        maxLength = 10
    )
    MaxLengthTextField(
        state = state,
        placeHolder = "아이디",
    )
}


