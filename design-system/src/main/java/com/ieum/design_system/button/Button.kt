package com.ieum.design_system.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.R
import com.ieum.design_system.theme.Gray100
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.theme.Gray300
import com.ieum.design_system.theme.Gray50
import com.ieum.design_system.theme.Gray500
import com.ieum.design_system.theme.Gray950
import com.ieum.design_system.theme.Lime100
import com.ieum.design_system.theme.Lime200
import com.ieum.design_system.theme.Lime400
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.Slate950
import com.ieum.design_system.theme.White

@Composable
fun IEUMButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    enabled: Boolean = true,
    roundedCorner: Boolean = true,
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = colors,
        enabled = enabled,
        shape = if (roundedCorner) MaterialTheme.shapes.medium else RectangleShape,
        border = border,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = if (enabled) colors.contentColor else colors.disabledContentColor,
        )
    }
}

@Composable
fun DarkButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    roundedCorner: Boolean = true,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier,
        text = text,
        colors = ButtonDefaults.buttonColors(
            containerColor = Slate900,
            contentColor = White,
            disabledContainerColor = Color(0xFF023651).copy(alpha = 0.2f),
            disabledContentColor = White,
        ),
        enabled = enabled,
        roundedCorner = roundedCorner,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) Slate950 else Color.Transparent,
        ),
        onClick = onClick
    )
}

@Composable
internal fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.skip),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray50,
            contentColor = Gray950,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Gray100,
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
            containerColor = Lime400,
            contentColor = Gray950,
            disabledContainerColor = Gray100,
            disabledContentColor = Gray300,
        ),
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) Lime500 else Gray200
        ),
        onClick = onClick,
    )
}

@Composable
internal fun LightNextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.next),
        colors = ButtonDefaults.buttonColors(
            containerColor = Lime100,
            contentColor = Gray950,
            disabledContainerColor = Lime100,
            disabledContentColor = Gray950,
        ),
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            color = Lime200,
        ),
        onClick = onClick,
    )
}

@Composable
fun SkipOrNextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onNext: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (enabled.not()) {
            SkipButton(
                modifier = Modifier.weight(1f),
                onClick = onNext
            )
        }
        LightNextButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
            onClick = onNext
        )
    }
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
                style = MaterialTheme.typography.labelMedium,
                color = Lime500,
            )
            Text(
                text = stringResource(R.string.selected_n_completed),
                style = MaterialTheme.typography.labelMedium,
                color = Gray500,
            )
        }
        NextButton(
            enabled = enabled,
            onClick = onClick,
        )
    }
}