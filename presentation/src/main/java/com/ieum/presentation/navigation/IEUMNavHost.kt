package com.ieum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ieum.presentation.screen.IEUMAppState
import com.ieum.presentation.screen.auth.nestedAuthGraph
import com.ieum.presentation.screen.main.nestedMainGraph

@Composable
fun IEUMNavHost(
    modifier: Modifier = Modifier,
    appState: IEUMAppState,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = ScreenGraph.Main,
    ) {
        nestedAuthGraph(appState)
        nestedMainGraph(appState)
    }
}