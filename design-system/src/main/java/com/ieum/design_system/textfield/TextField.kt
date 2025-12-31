package com.ieum.design_system.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.theme.Gray300
import com.ieum.design_system.theme.Lime100
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White

@Composable
private fun TextFieldPlaceHolder(
    text: String,
    textStyle: TextStyle,
) {
    Text(
        text = text,
        style = textStyle,
        color = Gray300,
    )
}

private val textSelectionColors = TextSelectionColors(
    handleColor = Lime500,
    backgroundColor = Lime100,
)

@Composable
fun IEUMTextField(
    modifier: Modifier = Modifier,
    state: ITextFieldState,
    placeHolder: String,
    singleLine: Boolean,
    textStyle: TextStyle,
    innerPadding: PaddingValues,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
) {
    var isFocused by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = state.typedText,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            textStyle = textStyle,
            onValueChange = state::typeText,
        ) { innerTextField ->
            Box(
                modifier = Modifier
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
                    .padding(innerPadding),
                contentAlignment = Alignment.TopStart,
            ) {
                if (state.typedText.isEmpty()) {
                    TextFieldPlaceHolder(
                        text = placeHolder,
                        textStyle = textStyle,
                    )
                } else {
                    innerTextField()
                }
            }
        }
    }
}

@Composable
fun MaxLengthTextField(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
    placeHolder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        IEUMTextField(
            state = state,
            placeHolder = placeHolder,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge,
            innerPadding = PaddingValues(horizontal = 18.dp, vertical = 24.dp),
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

@Composable
fun MultiLineTextField(
    modifier: Modifier = Modifier,
    state: ITextFieldState,
    placeHolder: String,
) {
    IEUMTextField(
        modifier = modifier,
        state = state,
        placeHolder = placeHolder,
        singleLine = false,
        textStyle = MaterialTheme.typography.bodySmall,
        innerPadding = PaddingValues(all = 18.dp),
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Unspecified,
    )
}

@Composable
fun StylelessTextField(
    modifier: Modifier = Modifier,
    state: ITextFieldState,
    placeHolder: String,
    singleLine: Boolean,
    textStyle: TextStyle,
    imeAction: ImeAction,
) {
    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.typedText,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
            ),
            textStyle = textStyle,
            onValueChange = state::typeText,
        ) { innerTextField ->
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart,
            ) {
                if (state.typedText.isEmpty()) {
                    TextFieldPlaceHolder(
                        text = placeHolder,
                        textStyle = textStyle,
                    )
                } else {
                    innerTextField()
                }
            }
        }
    }
}