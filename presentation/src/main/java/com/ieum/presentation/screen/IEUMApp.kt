package com.ieum.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ieum.presentation.navigation.IEUMNavHost

@Composable
fun IEUMApp(
    appState: IEUMAppState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IEUMNavHost(appState = appState)
    }
}