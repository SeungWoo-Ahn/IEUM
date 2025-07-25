package com.ieum.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun IEUMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = IEUMTypography,
        shapes = IEUMShapes,
        content = content,
    )
}