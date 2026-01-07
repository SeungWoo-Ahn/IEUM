package com.ieum.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ieum.presentation.navigation.IEUMNavHost

@Composable
fun IEUMApp(
    appState: IEUMAppState,
    isAuthenticated: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        IEUMNavHost(
            appState = appState,
            isAuthenticated = isAuthenticated,
        )
    }
}