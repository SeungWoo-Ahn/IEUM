package com.ieum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ieum.presentation.screen.IEUMAppState
import com.ieum.presentation.screen.auth.navigateToAuthGraph
import com.ieum.presentation.screen.auth.nestedAuthGraph
import com.ieum.presentation.screen.main.nestedMainGraph

@Composable
fun IEUMNavHost(
    modifier: Modifier = Modifier,
    appState: IEUMAppState,
    isAuthenticated: Boolean,
) {
    val startDestination = remember {
        if (isAuthenticated) ScreenGraph.Main else ScreenGraph.Auth
    }

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated.not()) {
            appState.navController.navigateToAuthGraph()
        }
    }

    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        nestedAuthGraph(appState)
        nestedMainGraph(appState)
    }
}