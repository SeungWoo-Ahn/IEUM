package com.ieum.design_system.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ieum.design_system.R

@Composable
fun IEUMButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    enabled: Boolean = true,
    roundCorner: Boolean = true,
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = colors,
        enabled = enabled,
        shape = if (roundCorner) {
            RoundedCornerShape(16.dp)
        } else {
            RectangleShape
        },
        border = border,
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun WhiteButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier,
        text = text,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        onClick = onClick
    )
}

@Preview
@Composable
internal fun WhiteButtonPreview() {
    WhiteButton(
        text = "회원 가입",
        onClick = {}
    )
}

@Composable
fun BlackButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier,
        text = text,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@Preview
@Composable
internal fun BlackButtonPreview() {
    BlackButton(
        text = "회원 가입",
        onClick = {}
    )
}

@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.skip),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.Black,
        ),
        onClick = onClick
    )
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.next),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Black,
            disabledContainerColor = Color.Green,
            disabledContentColor = Color.Black,
        ),
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
fun SkipOrNextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSkip: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (enabled.not()) {
            SkipButton(
                modifier = Modifier.weight(1f),
                onClick = onSkip
            )
        }
        NextButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
            onClick = onNext
        )
    }
}

@Preview
@Composable
internal fun SkipOrNextButtonPreview() {
    var enabled by remember { mutableStateOf(false) }
    SkipOrNextButton(
        enabled = enabled,
        onSkip = { enabled = true },
        onNext = { enabled = false },
    )
}

@Composable
fun SelectedCountButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    selectedCount: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$selectedCount",
                color = Color.Green
            )
            Text(
                text = stringResource(R.string.selected_n_completed)
            )
        }
        IEUMButton(
            text = stringResource(R.string.next),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray
            ),
            enabled = enabled,
            onClick = onClick
        )
    }
}

@Preview
@Composable
internal fun SelectedCountButtonPreview() {
    var enabled by remember { mutableStateOf(true) }
    SelectedCountButton(
        enabled = enabled,
        selectedCount = 1,
        onClick = { enabled = false }
    )
}