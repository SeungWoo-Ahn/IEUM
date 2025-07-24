package com.ieum.design_system.progressbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
private fun IEUMProgressBar(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        strokeWidth = 3.dp,
        color = Color.Green,
        trackColor = Color.Transparent,
    )
}

@Composable
fun IEUMLoadingComponent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        IEUMProgressBar()
    }
}