package com.ieum.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.util.noRippleClickable

@Composable
fun SettingButton(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray200,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(all = 20.dp)
            .noRippleClickable(onClick = onClick),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}