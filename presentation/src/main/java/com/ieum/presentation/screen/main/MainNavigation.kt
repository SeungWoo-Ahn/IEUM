package com.ieum.presentation.screen.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.navigation.ScreenGraph
import com.ieum.presentation.screen.IEUMAppState

fun NavGraphBuilder.nestedMainGraph(appState: IEUMAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.PostTreatmentRecords(null)) {
        composable<MainScreen.Home> {

        }
        composable<MainScreen.PostTreatmentRecords> {

        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToPostTreatmentRecordsScreen(id: String? = null) =
    navigate(MainScreen.PostTreatmentRecords(id))